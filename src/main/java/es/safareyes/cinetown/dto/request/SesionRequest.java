package es.safareyes.cinetown.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record SesionRequest(
        @NotNull(message = "Debe especificar la película")
        Long idPelicula,

        @NotNull(message = "Debe especificar la sala")
        Long idSala,

        @NotNull(message = "El precio base es obligatorio")
        @DecimalMin(value = "0.00", inclusive = false, message = "El precio debe ser mayor que 0")
        BigDecimal precioBase,

        @NotNull(message = "La fecha y hora de la sesión son obligatorias")
        @Future(message = "La sesión debe programarse para una fecha futura")
        LocalDateTime fechaHora
) {
}
