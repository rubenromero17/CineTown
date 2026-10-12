package es.safareyes.cinetown.dto;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SalaDTO {
    private Integer id;
    private Integer numeroSala;
    private Integer capacidad;
}
