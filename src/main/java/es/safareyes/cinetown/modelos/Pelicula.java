package es.safareyes.cinetown.modelos;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pelicula")
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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "generos_peliculas",
            joinColumns = @JoinColumn(name = "id_pelicula", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "id_genero", nullable = false)
    )
    private Set<Genero> generos = new HashSet<>();

    @OneToMany(mappedBy = "pelicula", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Sesion> sesiones;
}