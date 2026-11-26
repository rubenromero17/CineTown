package es.safareyes.cinetown.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompraDTO {
    private Integer idCompra;
    private String codigo;
    private Integer idSesion;
    private String tituloPelicula;
    private Integer numeroSala;
    private LocalDate dia;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime horaSesion;

    private Integer idUsuario;
    private String email;
    private Integer cantidad;
    private Double precioUnitario;
    private Double total;
    private String estado;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaCompra;
}