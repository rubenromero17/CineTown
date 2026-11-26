package es.safareyes.cinetown.modelos;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "compra")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_compra")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_sesion", nullable = false)
    private Sesion sesion;

    @Column(name = "codigo", nullable = false, unique = true, length = 8)
    private String codigo;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "total", nullable = false)
    private Double total;

    @Column(name = "estado", nullable = false)
    private String estado;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "fecha_compra", nullable = false)
    private LocalDateTime fechaCompra;
}