package es.safareyes.cinetown.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompraDTO {
    private Integer id;
    private String codigo;
    private Integer cantidad;
    private Integer total;
    private String estado;
    private String email;
    private LocalDateTime fechaCompra;
    private SesionDTO sesion;
}
