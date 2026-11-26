package es.safareyes.cinetown.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecaudacionDTO {

    private Integer idPelicula;
    private String titulo;
    private Long entradasVendidas;
    private Double recaudacionTotal;


}