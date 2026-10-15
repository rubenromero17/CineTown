package es.safareyes.cinetown.repositorios;

import es.safareyes.cinetown.dto.GeneroConTotalPeliculasDTO;
import es.safareyes.cinetown.modelos.Genero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IGeneroRepository extends JpaRepository<Genero, Integer> {

    @Query("""
        SELECT new es.safareyes.cinetown.dto.GeneroConTotalPeliculasDTO(
            g.id,
            g.nombre,
            COUNT(p)
        )
        FROM Genero g
        LEFT JOIN g.pelicula p ON p.publicada = true
        GROUP BY g.id, g.nombre
        ORDER BY g.nombre ASC
    """)
    List<GeneroConTotalPeliculasDTO> findGeneroConTotalPeliculas();
}