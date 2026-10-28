package es.safareyes.cinetown.mapper;

import es.safareyes.cinetown.dto.request.SesionRequest;
import es.safareyes.cinetown.dto.response.SesionResponse;
import es.safareyes.cinetown.modelos.Sesion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SesionMapper {
    @Mapping(target = "idPelicula", source = "pelicula.id")
    @Mapping(target = "tituloPelicula", source = "pelicula.titulo")
    @Mapping(target = "idSala", source = "sala.id")
    @Mapping(target = "numeroSala", source = "sala.numeroSala")
    @Mapping(target = "precioDelDia", ignore = true)
    SesionResponse aResponse(Sesion sesion);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pelicula", ignore = true)
    @Mapping(target = "sala", ignore = true)
    @Mapping(target = "asientosDisponibles", ignore = true)
    Sesion aEntidad(SesionRequest peticion);
}
