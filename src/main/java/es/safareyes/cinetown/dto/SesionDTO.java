package es.safareyes.cinetown.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SesionDTO {
    private Integer idSesion;
    private Double precioBase;
    private Double precioAplicado;
    private LocalDate dia;
    private Integer horaSesion;
    private Integer finSesion;
    private Integer asientosDisponible;

    private DetallePeliculaDTO pelicula;
    private SalaDTO sala;
}
