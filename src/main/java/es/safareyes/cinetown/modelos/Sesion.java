package es.safareyes.cinetown.modelos;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "sesion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sesion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sesion")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pelicula", nullable = false)
    private Pelicula pelicula;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_salas", nullable = false)
    private Salas sala;

    @Column(name = "precio_base", nullable = false)
    private BigDecimal precioBase;

    @Column(name = "dia", nullable = false)
    private String dia;

    @Column(name = "aientos_disponibles", nullable = false)
    private Integer asientosDisponibles;

    @Column(name = "hora_sesion", nullable = false)
    private Integer horaSesion;

    @Column(name = "fin_sesion", nullable = false)
    private Long finSesion;
}