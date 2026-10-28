package es.safareyes.cinetown.dto.response;

import java.math.BigDecimal;

public record RecaudacionPeliculaResponse(
        Long idPelicula,
        String titulo,
        Long entradasVendidas,
        BigDecimal recaudacionTotal
) {

}
