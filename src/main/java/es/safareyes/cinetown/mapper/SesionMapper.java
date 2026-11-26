package es.safareyes.cinetown.mapper;

import es.safareyes.cinetown.dto.SesionDTO;
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
}

