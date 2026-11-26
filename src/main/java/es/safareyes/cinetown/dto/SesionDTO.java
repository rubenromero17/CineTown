package es.safareyes.cinetown.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SesionDTO {
    private Integer idSesion;
    private Double precioBase;
    private Double precioAplicado;
    private LocalDate dia;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime horaSesion;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime finSesion;

    private Integer asientosDisponible;

    private DetallePeliculaDTO pelicula;
    private SalaDTO sala;
}