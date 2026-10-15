package es.safareyes.cinetown.modelos;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true) // Solo usa los campos marcados explícitamente
@ToString(exclude = {"generos", "sesiones"})      // Excluye las colecciones para evitar bucles y errores Lazy
@Entity
@Table(name="pelicula")
public class Pelicula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pelicula")
    @EqualsAndHashCode.Include // El equals y hashCode solo se basarán en el ID
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

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "generos_peliculas",
            joinColumns = @JoinColumn(name = "id_pelicula", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "id_genero", nullable = false)
    )
    private java.util.Set<Genero> generos;

    @OneToMany(mappedBy = "pelicula", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private java.util.Set<Sesion> sesiones;
}