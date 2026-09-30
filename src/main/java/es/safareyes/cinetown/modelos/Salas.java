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
@Table(name="Salas")
public class Salas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_salas")
    private Integer id;

    @Column(name = "capacidad", nullable = false)
    private Integer capacidad;

    @Column(name = "numero_sala", nullable = false)
    private Integer numeroSala;


}
