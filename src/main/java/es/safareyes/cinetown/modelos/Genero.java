package es.safareyes.cinetown.modelos;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"pelicula"})
@Entity
@Table(name="genero")
public class Genero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_genero")
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @ManyToMany(mappedBy = "generos")
    private Set<Pelicula> pelicula;
}