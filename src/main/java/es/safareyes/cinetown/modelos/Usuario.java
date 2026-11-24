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
@Table(name="usuario")
public class Usuario {
    public enum Rol {
        ADMIN,
        TAQUILLA,
        CLIENTE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer id;

    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @Column(name = "contrasenia", length = 100, nullable = false)
    private String contrasenia;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol", nullable = false)
    private Rol rol;

    @Column(name = "email", length = 100, nullable = false)
    private String email;

    @Column(name = "activo", nullable = false)
    private Boolean activo;

    @PrePersist
    void alCrear() {
        if (activo == null) {
            activo = true;
        }

    }
}
