package es.safareyes.cinetown.repositorios;

import es.safareyes.cinetown.modelos.Sesion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISesionRepository extends JpaRepository<Sesion, Integer> {

}
