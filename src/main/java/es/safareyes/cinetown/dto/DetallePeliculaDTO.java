package es.safareyes.cinetown.dto;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetallePeliculaDTO {
    private Integer id;

    @NotBlank(message = "El título de la película no puede estar vacío")
    @Size(max = 100, message = "El título de la película no puede exceder los 100 caracteres")
    private  String titulo;

    @NotBlank(message = "La sinopsis de la película no puede estar vacía")
    @Size(max = 1000, message = "La sinopsis de la película no puede exceder los 1000 caracteres")
    private String sipnosis;

    @NotNull(message = "La duración de la película no puede ser nula")
    @Min(value = 1, message = "La duración de la película debe ser al menos 1 minuto")
    private Integer duracion;

    @NotNull(message = "El año de la película no puede ser nulo")
    @Min(value = 1888, message = "El año de la película debe ser al menos 1888")
    private Integer anio;

    @NotBlank(message = "La clasificación de edad de la película no puede estar vacía")
    @Pattern(regexp = "^(A|7|12|16|18)$", message = "La clasificación de edad debe ser A, 7, 12, 16 o 18")
    private String clasificacionEdad;

    @NotNull(message = "El estado de la publicación de la película no puede ser nulo")
    private Boolean publicada;

    private Set<GeneroDTO> generos;

}
