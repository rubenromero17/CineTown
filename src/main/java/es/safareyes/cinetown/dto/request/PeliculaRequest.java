package es.safareyes.cinetown.dto.request;

import jakarta.validation.constraints.*;

import java.util.Set;

public record PeliculaRequest(
        @NotBlank(message = "El título es obligatorio")
        @Size(max = 255, message = "El título no puede superar los 255 caracteres")
        String titulo,

        @NotBlank(message = "La sinopsis es obligatoria")
        @Size(max = 1000, message = "La sinopsis no puede superar los 1000 caracteres")
        String sinopsis,

        @NotNull(message = "La duración es obligatoria")
        @Min(value = 1, message = "La duración mínima es de 1 minuto")
        @Max(value = 300, message = "La duración máxima es de 300 minutos")
        Integer duracion,

        @NotNull(message = "El año es obligatorio")
        @Min(value = 1895, message = "El año no es válido")
        Integer anio,

        @NotBlank(message = "La clasificación por edad es obligatoria")
        @Pattern(regexp = "TP|7|12|16|18", message = "Clasificación no válida (TP, 7, 12, 16, 18)")
        String clasificacionEdad,

        @NotNull(message = "Debe indicar al menos un género")
        @NotEmpty(message = "La lista de géneros no puede estar vacía")
        Set<Long> idsGeneros,

        Boolean publicada
) {
}
