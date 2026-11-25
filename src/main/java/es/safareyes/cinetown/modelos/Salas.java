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
@Builder
@Table(name="salas")
public class Salas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_salas")
    private Integer id;

    @Column(name = "capacidad", nullable = false)
    private Integer capacidad;

    @Column(name = "numero_sala", nullable = false, unique = true)
    private Integer numeroSala;


}
