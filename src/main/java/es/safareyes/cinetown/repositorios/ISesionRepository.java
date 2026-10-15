package es.safareyes.cinetown.repositorios;

import es.safareyes.cinetown.dto.OcupacionSesionDTO;
import es.safareyes.cinetown.modelos.Sesion;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
public interface ISesionRepository extends JpaRepository<Sesion, Integer> {

    @EntityGraph(attributePaths = {"pelicula", "sala"})
    List<Sesion> findByDiaAndPelicula_Id(String dia, Integer id);

    @EntityGraph(attributePaths = {"pelicula", "sala"})
    List<Sesion> findByDia(String dia);

    @EntityGraph(attributePaths = {"pelicula", "sala"})
    List<Sesion> findByPelicula_Id(Integer id);

    @Query(value = """
        SELECT 
            s.id_sesion AS idSesion,
            p.titulo AS tituloPelicula,
            sa.numero_sala AS numeroSala,
            sa.capacidad AS capacidad,
            s.asientos_disponibles AS asientosDisponibles,
            ROUND(((sa.capacidad - s.asientos_disponibles)::numeric / sa.capacidad::numeric) * 100, 2) AS porcentajeOcupacion 
        FROM sesion s 
        JOIN pelicula p ON s.id_pelicula = p.id_pelicula 
        JOIN salas sa ON s.id_salas = sa.id_salas 
        WHERE s.dia = :dia 
        ORDER BY porcentajeOcupacion DESC
        """, nativeQuery = true)
    List<Map<String, Object>> obtenerOcupacionPorFecha(@Param("dia") String dia);
}