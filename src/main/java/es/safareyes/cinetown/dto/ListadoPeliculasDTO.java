package es.safareyes.cinetown.dto;

import es.safareyes.cinetown.modelos.GenerosPeliculas;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListadoPeliculasDTO {
    private Integer id;

    private String titulo;
    private String clasificacion_edad;
    private Boolean publicada;

    Set<GeneroDTO> generos;


}
