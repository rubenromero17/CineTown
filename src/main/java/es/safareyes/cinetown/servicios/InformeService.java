package es.safareyes.cinetown.servicios;

import es.safareyes.cinetown.dto.OcupacionSesionDTO;
import es.safareyes.cinetown.modelos.Sesion;
import es.safareyes.cinetown.repositorios.ISesionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InformeService {

    private final ISesionRepository sesionRepository;

    public List<OcupacionSesionDTO> obtenerOcupacionPorFecha(LocalDate fecha) {
        List<Sesion> sesiones = sesionRepository.findByDiaOrderByHoraSesionAsc(fecha.toString());

        return sesiones.stream()
                .map(this::mapearOcupacion)
                .sorted(Comparator.comparing(OcupacionSesionDTO::getPorcentajeOcupacion).reversed())
                .toList();
    }

    private OcupacionSesionDTO mapearOcupacion(Sesion sesion) {
        int capacidad = sesion.getSala().getCapacidad();
        int disponibles = sesion.getAsientosDisponibles() != null ? sesion.getAsientosDisponibles() : 0;
        int ocupados = Math.max(0, capacidad - disponibles);

        double porcentaje = capacidad > 0
                ? Math.round(((double) ocupados / capacidad) * 10000.0) / 100.0
                : 0.0;

        return OcupacionSesionDTO.builder()
                .idSesion(sesion.getId())
                .tituloPelicula(sesion.getPelicula().getTitulo())
                .numeroSala(sesion.getSala().getNumeroSala())
                .horaSesion(sesion.getHoraSesion())
                .capacidad(capacidad)
                .asientosDisponibles(disponibles)
                .asientosOcupados(ocupados)
                .porcentajeOcupacion(porcentaje)
                .build();
    }
}