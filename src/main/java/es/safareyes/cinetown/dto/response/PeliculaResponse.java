package es.safareyes.cinetown.dto.response;

import java.util.Set;

public record PeliculaResponse(
        Long id,
        String titulo,
        String sinopsis,
        Integer duracion,
        Integer anio,
        String clasificacionEdad,
        boolean publicada,
        Set<String> generos
) {
}
