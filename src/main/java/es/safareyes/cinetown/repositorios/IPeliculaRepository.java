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

    // Listado paginado con filtros (RF02)
    @Query("SELECT new es.safareyes.cinetown.dto.ListadoPeliculasDTO(p.id, p.titulo, p.sinopsis, p.duracion, p.anio, p.clasificacionEdad, p.publicada) " +
            "FROM Pelicula p " +
            "WHERE (:titulo IS NULL OR LOWER(p.titulo) LIKE LOWER(CONCAT('%', :titulo, '%'))) " +
            "AND (:anio IS NULL OR p.anio = :anio) " +
            "AND (:genero IS NULL OR EXISTS (SELECT g FROM p.generos g WHERE g.nombre = :genero))")
    List<ListadoPeliculasDTO> findPeliculasWithFilters(@Param("titulo") String titulo, @Param("anio") Integer anio, @Param("genero") String genero);
}
