package es.safareyes.cinetown.mapper;

import es.safareyes.cinetown.dto.request.PeliculaRequest;
import es.safareyes.cinetown.dto.response.PeliculaResponse;
import es.safareyes.cinetown.modelos.Genero;
import es.safareyes.cinetown.modelos.Pelicula;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PeliculaMapper {
    @Mapping(target = "generos", source = "generos", qualifiedByName = "mapGenerosToStrings")
    PeliculaResponse aResponse(Pelicula pelicula);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "generos", ignore = true)
    Pelicula aEntidad(PeliculaRequest peticion);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "generos", ignore = true)
    void actualizar(@MappingTarget Pelicula destino, PeliculaRequest peticion);

    @Named("mapGenerosToStrings")
    default Set<String> mapGenerosToStrings(Set<Genero> generos) {
        if (generos == null) return Set.of();
        return generos.stream()
                .map(Genero::getNombre)
                .collect(Collectors.toSet());
    }

}
