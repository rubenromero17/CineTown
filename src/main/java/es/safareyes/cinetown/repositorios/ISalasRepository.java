package es.safareyes.cinetown.repositorios;

import es.safareyes.cinetown.modelos.Salas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISalasRepository extends JpaRepository<Salas, Integer> {
}
