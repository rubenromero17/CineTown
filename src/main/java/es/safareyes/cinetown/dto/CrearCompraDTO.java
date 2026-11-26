package es.safareyes.cinetown.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CrearCompraDTO {

    @NotNull(message = "El id de la sesión es obligatorio")
    private Integer idSesion;

    private Integer idUsuario;

    @Email(message = "El formato del email no es válido")
    private String email;

    @NotNull(message = "La cantidad de entradas es obligatoria")
    @Min(value = 1, message = "Debes comprar al menos 1 entrada")
    @Max(value = 10, message = "No se pueden comprar más de 10 entradas en una misma operación")
    private Integer cantidad;
}