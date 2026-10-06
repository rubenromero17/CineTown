package es.safareyes.cinetown.repositorios;

import es.safareyes.cinetown.dto.ListadoPeliculasDTO;
import es.safareyes.cinetown.modelos.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPeliculaRepository extends JpaRepository<Pelicula, Integer> {

    List<ListadoPeliculasDTO>findAllProjectedBy();

    List<ListadoPeliculasDTO>findByTitulo(String titulo);



}
