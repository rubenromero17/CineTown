package es.safareyes.cinetown.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GeneroDTO {
    private Integer id;

    @NotBlank(message = "El nombre del género no puede estar vacío")
    @Size(max = 50, message = "El nombre del género no puede exceder los 100 caracteres")
    private String nombre;


}
