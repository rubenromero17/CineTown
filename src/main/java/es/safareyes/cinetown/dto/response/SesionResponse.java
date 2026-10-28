package es.safareyes.cinetown.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record SesionResponse(
        Long id,
        Long idPelicula,
        String tituloPelicula,
        Long idSala,
        Integer numeroSala,
        BigDecimal precioBase,
        BigDecimal precioDelDia,
        LocalDateTime fechaHora,
        Integer asientosDisponibles
) {
}
