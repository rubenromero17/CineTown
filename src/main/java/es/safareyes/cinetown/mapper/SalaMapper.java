package es.safareyes.cinetown.mapper;

import es.safareyes.cinetown.dto.SalaDTO;
import es.safareyes.cinetown.modelos.Salas;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SalaMapper {
    SalaDTO toDto(Salas sala);

    List<SalaDTO> toDtoList(List<Salas> salas);
}
