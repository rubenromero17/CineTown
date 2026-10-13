package es.safareyes.cinetown.repositorios;

import es.safareyes.cinetown.dto.GeneroConTotalPeliculasDTO;
import es.safareyes.cinetown.modelos.GenerosPeliculas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IGeneroRepository extends JpaRepository<GenerosPeliculas, Integer> {
    @Query("""
        select new es.safareyes.cinetown.dto.GeneroConTotalPeliculasDTO(g.id, g.nombre, count(p))
        from Genero g
        left join g.pelicula p on p.publicada = true
        group by g.id, g.nombre
        order by g.nombre asc 
    """)
    List<GeneroConTotalPeliculasDTO> findGeneroConTotalPeliculas();
}
