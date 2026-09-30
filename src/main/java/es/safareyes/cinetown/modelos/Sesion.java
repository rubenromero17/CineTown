package es.safareyes.cinetown.modelos;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@NoArgsConstructor

@Entity
@Table(name="sesion")
public class Sesion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sesion")
    private Integer id;

    @Column(name = "precio_base", nullable = false)
    private Double precioBase;

    @Column(name = "dia", nullable = false)
    private String dia;

    @Column(name = "asientos_disponibles", nullable = false)
    private Integer asientosDisponibles;

    @Column(name = "hora_sesion", nullable = false)
    private Integer horaSesion;

    @Column(name = "fin_sesion", nullable = false)
    private Integer finSesion;

    @ManyToOne
    @JoinColumn(name = "id_pelicula", nullable = false)
    private Pelicula pelicula;
    @ManyToOne
    @JoinColumn(name = "id_salas", nullable = false)
    private Salas sala;


}
