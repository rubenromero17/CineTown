package es.safareyes.cinetown.repositorios;

import es.safareyes.cinetown.modelos.Pelicula;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IPeliculaRepository extends JpaRepository<Pelicula, Integer>, JpaSpecificationExecutor<Pelicula> {

    boolean existsByTituloIgnoreCase(String titulo);

    @Override
    @EntityGraph(attributePaths = {"generos"})
    Page<Pelicula> findAll(Specification<Pelicula> spec, Pageable pageable);

    @EntityGraph(attributePaths = {"generos"})
    @Query("SELECT p FROM Pelicula p WHERE p.id = :id")
    Optional<Pelicula> findWithGenerosByIdPelicula(@Param("id") Integer idPelicula);


}