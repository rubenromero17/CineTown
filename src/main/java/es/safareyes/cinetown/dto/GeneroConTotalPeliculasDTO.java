package es.safareyes.cinetown.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GeneroConTotalPeliculasDTO {
    private Integer id;
    private String nombre;
    private Long totalPeliculas;
}
