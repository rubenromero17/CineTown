package es.safareyes.cinetown.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecaudacionDTO {
    private Integer id;
    private String titulo;
    private Integer entradasVendidas;
    private Integer recaudacionTotal;
}
