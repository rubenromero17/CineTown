package es.safareyes.cinetown.repositorios;

import es.safareyes.cinetown.dto.RecaudacionDTO;
import es.safareyes.cinetown.modelos.Compra;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICompraRepository extends JpaRepository<Compra, Integer> {
    Optional<Compra> findByCodigo(Integer codigo);
    Page<Compra> findByEmailIgnoreCaseOrderByFechaCompraDesc(String email, Pageable pageable);

    @Query("""
    SELECT new es.safareyes.cinetown.dto.RecaudacionDTO(
        p.id,
        p.titulo,
        COALESCE(SUM(c.cantidad), 0L),
        COALESCE(SUM(c.total), 0)
    )
    FROM Compra c
    JOIN c.sesion s
    JOIN s.pelicula p
    WHERE p.id = :idPelicula AND c.estado = 'comprada'
    GROUP BY p.id, p.titulo
""")
    Optional<RecaudacionDTO> obtenerRecaudacionPelicula(@Param("idPelicula") Long idPelicula);
}
