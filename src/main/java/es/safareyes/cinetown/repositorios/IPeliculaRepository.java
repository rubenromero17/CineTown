package es.safareyes.cinetown.repositorios;

import es.safareyes.cinetown.dto.ListadoPeliculasDTO;
import es.safareyes.cinetown.modelos.Pelicula;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IPeliculaRepository extends JpaRepository<Pelicula, Integer>, JpaSpecificationExecutor<Pelicula> {

    @EntityGraph(attributePaths = {"generos"})
    @Query("SELECT DISTINCT p FROM Pelicula p LEFT JOIN p.generos g " +
            "WHERE (:titulo IS NULL OR LOWER(p.titulo) LIKE LOWER(CONCAT('%', :titulo, '%'))) " +
            "AND (:genero IS NULL OR LOWER(g.nombre) = LOWER(:genero)) " +
            "AND (:enCartelera IS NULL OR p.publicada = :enCartelera)")
    Page<Pelicula> buscarConFiltros(
            @Param("titulo") String titulo,
            @Param("genero") String genero,
            @Param("enCartelera") Boolean enCartelera,
            Pageable pageable);
    @Query("SELECT p FROM Pelicula p WHERE p.id = :id")
    Optional<Pelicula> findWithGenerosByIdPelicula(@Param("id") Long idPelicula);
}
