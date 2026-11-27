package es.safareyes.cinetown.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OcupacionSesionDTO {

    private Integer idSesion;
    private String tituloPelicula;
    private Integer numeroSala;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime horaSesion;

    private Integer capacidad;
    private Integer asientosDisponibles;
    private Integer asientosOcupados;
    private Double porcentajeOcupacion;
}