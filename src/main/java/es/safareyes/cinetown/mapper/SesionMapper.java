package es.safareyes.cinetown.mapper;

import es.safareyes.cinetown.dto.SesionDTO;
import es.safareyes.cinetown.dto.SesionTarifaAplicadaDTO;
import es.safareyes.cinetown.modelos.Sesion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {PeliculaMapper.class, SalaMapper.class})
public interface SesionMapper {

    @Mapping(source = "id", target = "idSesion")
    @Mapping(source = "asientosDisponibles", target = "asientosDisponible")
    SesionDTO toDto(Sesion sesion);

    List<SesionDTO> toDtoList(List<Sesion> sesiones);

    @Mapping(source = "id", target = "idSesion")
    @Mapping(source = "sala.capacidad", target = "capacidadSala")
    @Mapping(source = "asientosDisponibles", target = "asientosLibres")
    @Mapping(target = "tarifaAplicada", ignore = true)
    SesionTarifaAplicadaDTO toTarifaAplicadaDto(Sesion sesion);
}