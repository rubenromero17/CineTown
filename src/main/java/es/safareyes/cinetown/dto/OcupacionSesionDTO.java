package es.safareyes.cinetown.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OcupacionSesionDTO {
    private Integer idSesion;
    private String tituloPelicula;
    private Integer numeroSala;
    private Integer capacidad;
    private Integer asientosDisponibles;
    private Double porcentajeOcupacion;
}
