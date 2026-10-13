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

@Repository
public interface ISesionRepository extends JpaRepository<Sesion, Integer> {

    @EntityGraph(attributePaths = {"pelicula", "sala"})
    List<Sesion> findByDiaAndPelicula_Id(LocalDate dia, Integer id);

    @EntityGraph(attributePaths = {"pelicula", "sala"})
    List<Sesion> findByDia(LocalDate dia);

    @EntityGraph(attributePaths = {"pelicula", "sala"})
    List<Sesion> findByPelicula_Id(Integer id);

    @Query(nativeQuery = true, value = """
        SELECT 
            s.id_sesion AS idSesion,
            p.titulo AS tituloPelicula,
            sa.numero_sala AS numeroSala,
            sa.capacidad AS capacidad,
            s.aientos_disponibles AS asientosDisponibles,
            ROUND(((sa.capacidad - s.aientos_disponibles)::numeric / sa.capacidad::numeric) * 100, 2) AS porcentajeOcupacion
        FROM sesion s
        JOIN pelicula p ON s.id_pelicula = p.id_pelicula
        JOIN salas sa ON s.id_salas = sa.id_salas
        WHERE s.dia = :fecha
        ORDER BY porcentajeOcupacion DESC
    """)
    List<OcupacionSesionDTO> obtenerOcupacionPorFecha(@Param("fecha") String fecha);
}