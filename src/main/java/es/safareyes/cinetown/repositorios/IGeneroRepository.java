package es.safareyes.cinetown.repositorios;

import es.safareyes.cinetown.modelos.GenerosPeliculas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IGeneroRepository extends JpaRepository<GenerosPeliculas, Integer> {
}
