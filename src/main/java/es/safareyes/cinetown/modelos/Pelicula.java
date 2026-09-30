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
@Table(name="pelicula")
public class Pelicula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pelicula")
    private Integer id;

    @Column(name = "titulo", length = 100, nullable = false)
    private String titulo;

    @Column(name = "sinopsis", length = 200, nullable = false)
    private String sinopsis;

    @Column(name = "duracion", nullable = false)
    private Integer duracion;

    @Column(name = "anio", nullable = false)
    private Integer anio;

    @Column(name = "clasificacion_edad", length = 200, nullable = false)
    private String clasificacionEdad;

    @Column(name = "publicada", nullable = false)
    private Boolean publicada;


}
