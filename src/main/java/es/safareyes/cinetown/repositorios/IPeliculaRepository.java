package es.safareyes.cinetown.repositorios;

import es.safareyes.cinetown.dto.ListadoPeliculasDTO;
import es.safareyes.cinetown.modelos.Pelicula;
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

    @Query("SELECT p FROM Pelicula p WHERE p.id = :id")
    Optional<Pelicula> findWithGenerosByIdPelicula(@Param("id") Long idPelicula);
}
