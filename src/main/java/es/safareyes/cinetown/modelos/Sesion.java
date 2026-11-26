package es.safareyes.cinetown.modelos;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "sesion")
public class Sesion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sesion")
    private Integer id;

    @Column(name = "precio_base", nullable = false)
    private Double precioBase;

    @Column(name = "precio_aplicado", nullable = false)
    private Double precioAplicado;

    @Column(name = "dia", nullable = false)
    private String dia;

    @Column(name = "hora_sesion", nullable = false)
    private LocalTime horaSesion;

    @Column(name = "fin_sesion", nullable = false)
    private LocalTime finSesion;

    @Column(name = "asientos_disponibles", nullable = false)
    private Integer asientosDisponibles;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pelicula", nullable = false)
    private Pelicula pelicula;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_salas", nullable = false)
    private Salas sala;
}