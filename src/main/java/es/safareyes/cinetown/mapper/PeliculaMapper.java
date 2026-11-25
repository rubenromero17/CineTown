package es.safareyes.cinetown.mapper;

import es.safareyes.cinetown.dto.DetallePeliculaDTO;
import es.safareyes.cinetown.dto.GeneroDTO;
import es.safareyes.cinetown.dto.ListadoPeliculasDTO;
import es.safareyes.cinetown.modelos.Genero;
import es.safareyes.cinetown.modelos.Pelicula;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface PeliculaMapper {

    @Mapping(target = "generos", source = "generos")
    ListadoPeliculasDTO toListadoDto(Pelicula pelicula);

    DetallePeliculaDTO toDetalleDto(Pelicula pelicula);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "generos", ignore = true)
    @Mapping(target = "sesiones", ignore = true)
    Pelicula toEntity(DetallePeliculaDTO dto);

    GeneroDTO toGeneroDto(Genero genero);

    default List<String> mapGeneros(Set<Genero> generos) {
        if (generos == null) {
            return List.of();
        }
        return generos.stream()
                .map(Genero::getNombre)
                .toList();
    }
}