package es.safareyes.cinetown.mapper;

import es.safareyes.cinetown.dto.CompraDTO;
import es.safareyes.cinetown.modelos.Compra;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CompraMapper {

    @Mapping(source = "id", target = "idCompra")
    @Mapping(source = "usuario.id", target = "idUsuario")
    @Mapping(source = "sesion.id", target = "idSesion")
    @Mapping(source = "sesion.pelicula.titulo", target = "tituloPelicula")
    @Mapping(source = "sesion.sala.numeroSala", target = "numeroSala")
    @Mapping(source = "sesion.dia", target = "dia")
    @Mapping(source = "sesion.horaSesion", target = "horaSesion")
    @Mapping(target = "precioUnitario", ignore = true)
    CompraDTO toDto(Compra compra);

    List<CompraDTO> toDtoList(List<Compra> compras);
}