package es.safareyes.cinetown.repositorios;

import es.safareyes.cinetown.dto.RecaudacionDTO;
import es.safareyes.cinetown.modelos.Compra;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICompraRepository extends JpaRepository<Compra, Integer> {

    boolean existsByCodigo(String codigo);

    @EntityGraph(attributePaths = {"usuario", "sesion", "sesion.pelicula", "sesion.sala"})
    Optional<Compra> findByCodigo(String codigo);

    @EntityGraph(attributePaths = {"usuario", "sesion", "sesion.pelicula", "sesion.sala"})
    Optional<Compra> findByCodigoIgnoreCase(String codigo);

    Page<Compra> findByEmailIgnoreCaseOrderByFechaCompraDesc(String email, Pageable pageable);

    @Query("SELECT NEW es.safareyes.cinetown.dto.RecaudacionDTO(p.id, p.titulo, SUM(c.cantidad), SUM(c.total)) " +
            "FROM Compra c " +
            "JOIN c.sesion s " +
            "JOIN s.pelicula p " +
            "WHERE p.id = :idPelicula AND c.estado = 'comprada' " +
            "GROUP BY p.id, p.titulo")
    Optional<RecaudacionDTO> obtenerRecaudacionPelicula(@Param("idPelicula") Integer idPelicula);
}