package es.safareyes.cinetown.servicios;

import es.safareyes.cinetown.dto.CrearSesionDTO;
import es.safareyes.cinetown.dto.SesionDTO;
import es.safareyes.cinetown.dto.SesionTarifaAplicadaDTO;
import es.safareyes.cinetown.mapper.SesionMapper;
import es.safareyes.cinetown.modelos.Pelicula;
import es.safareyes.cinetown.modelos.Salas;
import es.safareyes.cinetown.modelos.Sesion;
import es.safareyes.cinetown.repositorios.IPeliculaRepository;
import es.safareyes.cinetown.repositorios.ISalasRepository;
import es.safareyes.cinetown.repositorios.ISesionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SesionService {

    private static final int MINUTOS_LIMPIEZA = 20;

    private final ISesionRepository sesionRepository;
    private final IPeliculaRepository peliculaRepository;
    private final ISalasRepository salasRepository;
    private final SesionMapper sesionMapper;

    public List<SesionDTO> listar(LocalDate dia, Integer idPelicula) {
        return listar(dia, idPelicula, null);
    }

    public List<SesionDTO> listar(LocalDate dia, Integer idPelicula, Boolean soloDisponibles) {
        List<Sesion> sesiones;
        if (dia != null && idPelicula != null) {
            sesiones = sesionRepository.findByDiaAndPelicula_IdOrderByHoraSesionAsc(dia.toString(), idPelicula);
        } else if (dia != null) {
            sesiones = sesionRepository.findByDiaOrderByHoraSesionAsc(dia.toString());
        } else if (idPelicula != null) {
            sesiones = sesionRepository.findByPelicula_IdOrderByDiaAscHoraSesionAsc(idPelicula);
        } else {
            sesiones = sesionRepository.findAllByOrderByDiaAscHoraSesionAsc();
        }

        if (Boolean.TRUE.equals(soloDisponibles)) {
            sesiones = sesiones.stream()
                    .filter(s -> s.getAsientosDisponibles() != null && s.getAsientosDisponibles() > 0)
                    .toList();
        }

        if (sesiones.isEmpty()) {
            throw new EntityNotFoundException(construirMensajeSinSesiones(dia, idPelicula, soloDisponibles));
        }

        return sesionMapper.toDtoList(sesiones);
    }

    private String construirMensajeSinSesiones(LocalDate dia, Integer idPelicula, Boolean soloDisponibles) {
        String sufijoLibres = Boolean.TRUE.equals(soloDisponibles) ? " con asientos libres" : "";

        if (dia != null && idPelicula != null) {
            return "No hay sesiones" + sufijoLibres + " ese día (" + dia + ") para la película con id " + idPelicula;
        }
        if (dia != null) {
            return "No hay sesiones" + sufijoLibres + " programadas para el día " + dia;
        }
        if (idPelicula != null) {
            return "No hay sesiones" + sufijoLibres + " programadas para la película con id " + idPelicula;
        }
        return "No hay sesiones" + sufijoLibres + " disponibles";
    }

    public SesionTarifaAplicadaDTO obtenerPorId(Integer id) {
        Sesion sesion = sesionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Sesión no encontrada con id: " + id));

        SesionTarifaAplicadaDTO dto = sesionMapper.toTarifaAplicadaDto(sesion);
        LocalDate fecha = LocalDate.parse(sesion.getDia());
        dto.setPrecioAplicado(calcularPrecioAplicado(sesion.getPrecioBase(), fecha, sesion.getHoraSesion()));
        dto.setTarifaAplicada(obtenerDescripcionTarifa(fecha, sesion.getHoraSesion()));
        return dto;
    }

    @Transactional
    public SesionDTO programar(CrearSesionDTO dto) {
        Pelicula pelicula = peliculaRepository.findWithGenerosByIdPelicula(dto.getIdPelicula())
                .orElseThrow(() -> new EntityNotFoundException("Película no encontrada con id: " + dto.getIdPelicula()));

        if (!Boolean.TRUE.equals(pelicula.getPublicada())) {
            throw new IllegalStateException("No se puede programar una sesión para una película que no está en cartelera");
        }

        Salas sala = salasRepository.findById(dto.getIdSala())
                .orElseThrow(() -> new EntityNotFoundException("Sala no encontrada con id: " + dto.getIdSala()));

        LocalTime finSesion = dto.getHoraSesion().plusMinutes((long) pelicula.getDuracion() + MINUTOS_LIMPIEZA);
        if (finSesion.isBefore(dto.getHoraSesion())) {
            throw new IllegalStateException("La sesión y su tiempo de limpieza no pueden superar las 23:59 del mismo día");
        }

        String diaStr = dto.getDia().toString();

        if (sesionRepository.existeSolapamiento(sala.getId(), diaStr, dto.getHoraSesion(), finSesion)) {
            throw new IllegalStateException("La sala " + sala.getNumeroSala() + " ya tiene una sesión programada en ese horario");
        }

        double precioAplicado = calcularPrecioAplicado(dto.getPrecioBase(), dto.getDia(), dto.getHoraSesion());

        Sesion sesion = Sesion.builder()
                .pelicula(pelicula)
                .sala(sala)
                .dia(diaStr)
                .horaSesion(dto.getHoraSesion())
                .finSesion(finSesion)
                .precioBase(dto.getPrecioBase())
                .precioAplicado(precioAplicado)
                .asientosDisponibles(sala.getCapacidad())
                .build();

        Sesion guardada = sesionRepository.save(sesion);
        return sesionMapper.toDto(guardada);
    }

    private double calcularPrecioAplicado(double precioBase, LocalDate dia, LocalTime horaSesion) {
        DayOfWeek diaSemana = dia.getDayOfWeek();
        double precio = precioBase;

        if (diaSemana == DayOfWeek.WEDNESDAY) {
            precio = precioBase * 0.70;
        } else if (horaSesion.isBefore(LocalTime.of(14, 0))) {
            precio = precioBase * 0.80;
        } else if (diaSemana == DayOfWeek.FRIDAY || diaSemana == DayOfWeek.SATURDAY || diaSemana == DayOfWeek.SUNDAY) {
            precio = precioBase * 1.15;
        }

        return Math.round(precio * 100.0) / 100.0;
    }

    private String obtenerDescripcionTarifa(LocalDate dia, LocalTime horaSesion) {
        DayOfWeek diaSemana = dia.getDayOfWeek();

        if (diaSemana == DayOfWeek.WEDNESDAY) {
            return "Día del espectador (-30%)";
        }
        if (horaSesion.isBefore(LocalTime.of(14, 0))) {
            return "Sesión matinal (-20%)";
        }
        if (diaSemana == DayOfWeek.FRIDAY || diaSemana == DayOfWeek.SATURDAY || diaSemana == DayOfWeek.SUNDAY) {
            return "Fin de semana (+15%)";
        }
        return "Tarifa normal";
    }
}