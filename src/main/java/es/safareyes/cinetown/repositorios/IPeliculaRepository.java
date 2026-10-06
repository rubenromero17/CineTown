package es.safareyes.cinetown.repositorios;

import es.safareyes.cinetown.dto.ListadoPeliculasDTO;
import es.safareyes.cinetown.modelos.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IPeliculaRepository extends JpaRepository<Pelicula, Integer> {

    List<ListadoPeliculasDTO>findAllProjectedBy();

    List<ListadoPeliculasDTO>findByTitulo(String titulo);

    @Query("SELECT p FROM Pelicula p LEFT JOIN FETCH p.generos WHERE p.id = :id")
    Optional<Pelicula> findByIdConGeneros(@Param("id") Integer id);




}
