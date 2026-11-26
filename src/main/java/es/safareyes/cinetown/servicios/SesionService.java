/*
Miércoles (Día del espectador): 30 % de descuento (7.00 €).

Sesión matinal (antes de las 14:00, excepto miércoles): 20 % de descuento (8.00 €).

Fin de semana (viernes, sábado o domingo, a partir de las 14:00): 15 % de suplemento (11.50 €).

Resto de días y horas: se mantiene igual que precioBase (10.00 €).
 */

package es.safareyes.cinetown.servicios;

import es.safareyes.cinetown.dto.CrearSesionDTO;
import es.safareyes.cinetown.dto.SesionDTO;
import es.safareyes.cinetown.mapper.SesionMapper;
import es.safareyes.cinetown.modelos.Pelicula;
import es.safareyes.cinetown.modelos.Salas;
import es.safareyes.cinetown.modelos.Sesion;
import es.safareyes.cinetown.repositorios.IPeliculaRepository;
import es.safareyes.cinetown.repositorios.ISalasRepository;
import es.safareyes.cinetown.repositorios.ISesionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        List<Sesion> sesiones;
        if (dia != null && idPelicula != null) {
            sesiones = sesionRepository.findByDiaAndPelicula_Id(dia.toString(), idPelicula);
        } else if (dia != null) {
            sesiones = sesionRepository.findByDia(dia.toString());
        } else if (idPelicula != null) {
            sesiones = sesionRepository.findByPelicula_Id(idPelicula);
        } else {
            sesiones = sesionRepository.findAll();
        }
        return sesionMapper.toDtoList(sesiones);
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
}