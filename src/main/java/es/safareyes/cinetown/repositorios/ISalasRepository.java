package es.safareyes.cinetown.repositorios;

import es.safareyes.cinetown.modelos.Salas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ISalasRepository extends JpaRepository<Salas, Integer> {
    List<Salas> findAllById(Integer id);

    Optional<Salas> findByNumeroSala(Integer numeroSala);

    boolean existsByNumeroSala(Integer numeroSala);


}
