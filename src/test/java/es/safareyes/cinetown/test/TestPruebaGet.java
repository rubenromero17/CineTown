package es.safareyes.cinetown.test;

import es.safareyes.cinetown.FIltrosDinamicos.PeliculaSpecification;
import es.safareyes.cinetown.dto.GeneroConTotalPeliculasDTO;
import es.safareyes.cinetown.dto.OcupacionSesionDTO;
import es.safareyes.cinetown.dto.RecaudacionDTO;
import es.safareyes.cinetown.modelos.Compra;
import es.safareyes.cinetown.modelos.Pelicula;
import es.safareyes.cinetown.modelos.Salas;
import es.safareyes.cinetown.modelos.Sesion;
import es.safareyes.cinetown.repositorios.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@SpringBootTest
public class TestPruebaGet {

    @Autowired
    private IPeliculaRepository peliculaRepository;

    @Autowired
    private IGeneroRepository generoRepository;

    @Autowired
    private ISalasRepository salaRepository;

    @Autowired
    private ISesionRepository sesionRepository;

    @Autowired
    private ICompraRepository compraRepository;

    @Test
    @DisplayName("GET 1: Buscar películas con Specification dinámica y Paginación (Q6 + Q7)")
    void testListarPeliculasConFiltrosYPaginacion() {
        Specification<Pelicula> spec = Specification
                .where(PeliculaSpecification.conTitulo("Viaje"))
                .and(PeliculaSpecification.estaPublicada(true));

        Pageable pageable = PageRequest.of(0, 5);
        Page<Pelicula> resultado = peliculaRepository.findAll(spec, pageable);

        System.out.println("--- [TEST 1] Películas encontradas: " + resultado.getContent());

        assertThat(resultado).isNotNull();
        assertThat(resultado.getContent()).isNotEmpty();
        assertThat(resultado.getContent().get(0).getTitulo()).containsIgnoringCase("Viaje");
    }

    @Test
    @Transactional
    @DisplayName("GET 2: Obtener película por ID cargando sus géneros con EntityGraph (Q8)")
    void testFindWithGenerosByIdPelicula() {
        Optional<Pelicula> peliculaOpt = peliculaRepository.findWithGenerosByIdPelicula(1L);

        System.out.println("--- [TEST 2] Película con géneros: " + peliculaOpt);

        assertThat(peliculaOpt).isPresent();
        Pelicula pelicula = peliculaOpt.get();
        assertThat(pelicula.getTitulo()).isEqualTo("El Viaje Perdido");
        assertThat(pelicula.getGeneros()).isNotEmpty();
    }

    @Test
    @DisplayName("GET 6: Obtener géneros con conteo de películas en cartelera (Q2 + Q5)")
    void testFindGeneroConTotalPeliculas() {
        List<GeneroConTotalPeliculasDTO> resultado = generoRepository.findGeneroConTotalPeliculas();

        System.out.println("--- [TEST 6] Géneros con total de películas: " + resultado);

        assertThat(resultado).isNotNull();
        assertThat(resultado).isNotEmpty();
        assertThat(resultado.get(0).getNombre()).isNotNull();
        assertThat(resultado.get(0).getTotalPeliculas()).isGreaterThanOrEqualTo(0L);
    }

    @Test
    @DisplayName("GET 7: Listar todas las salas del cine")
    void testListarTodasLasSalas() {
        List<Salas> salas = salaRepository.findAll();

        System.out.println("--- [TEST 7] Salas encontradas: " + salas);

        assertThat(salas).isNotEmpty();
        assertThat(salas.size()).isGreaterThanOrEqualTo(10);
    }

    @Test
    @DisplayName("GET 9a: Buscar sesiones por fecha y película (Q1 + Q8)")
    void testFindByDiaAndPelicula() {
        String fecha = "2026-10-01";
        List<Sesion> sesiones = sesionRepository.findByDiaAndPelicula_Id(fecha, 1);

        System.out.println("--- [TEST 9a] Sesiones por fecha y película: " + sesiones);

        assertThat(sesiones).isNotEmpty();
        assertThat(sesiones.get(0).getPelicula().getId()).isEqualTo(1);
        assertThat(sesiones.get(0).getSala()).isNotNull();
    }

    @Test
    @DisplayName("GET 9b: Buscar sesiones solo por fecha (Q1 + Q8)")
    void testFindByDia() {
        String fecha = "2026-10-05";
        List<Sesion> sesiones = sesionRepository.findByDia(fecha);

        System.out.println("--- [TEST 9b] Sesiones por fecha: " + sesiones);

        assertThat(sesiones).isNotEmpty();
    }
    @Test
    @DisplayName("GET 10: Obtener sesión por ID")
    void testObtenerSesionPorId() {
        Optional<Sesion> sesionOpt = sesionRepository.findById(1);

        System.out.println("--- [TEST 10] Sesión por ID: " + sesionOpt);

        assertThat(sesionOpt).isPresent();
        assertThat(sesionOpt.get().getPrecioBase()).isNotNull();
    }

    @Test
    @DisplayName("GET 12: Buscar compra por código alfanumérico (Q1)")
    void testFindByCodigo() {
        Optional<Compra> compraOpt = compraRepository.findByCodigo("CMP00001");

        System.out.println("--- [TEST 12] Compra por código: " + compraOpt);

        assertThat(compraOpt).isPresent();
        assertThat(compraOpt.get().getEmail()).isEqualTo("carlos.garcia@email.com");
    }

    @Test
    @DisplayName("GET 13: Listar compras paginadas por email de usuario (Q1 + Q6)")
    void testFindByEmailPaginado() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<Compra> compras = compraRepository.findByEmailIgnoreCaseOrderByFechaCompraDesc("carlos.garcia@email.com", pageable);

        System.out.println("--- [TEST 13] Compras paginadas: " + compras.getContent());

        assertThat(compras).isNotEmpty();
        assertThat(compras.getContent().get(0).getCodigo()).isEqualTo("CMP00001");
    }

    @Test
    @DisplayName("GET 15: Obtener recaudación total por película (Q2 + Q5)")
    void testObtenerRecaudacionPelicula() {
        Optional<RecaudacionDTO> informeOpt = compraRepository.obtenerRecaudacionPelicula(1);

        System.out.println("--- [TEST 15] Recaudación película: " + informeOpt);

        assertThat(informeOpt).isPresent();
        RecaudacionDTO informe = informeOpt.get();

        assertThat(informe.getId()).isEqualTo(1);
        assertThat(informe.getEntradasVendidas()).isGreaterThanOrEqualTo(1);
    }

    @Test
    @DisplayName("GET: Obtener ocupación por fecha")
    void testObtenerOcupacionPorFecha() {
        String fecha = "2026-10-01";
        List<Map<String, Object>> resultados = sesionRepository.obtenerOcupacionPorFecha(fecha);

        System.out.println("--- [TEST Ocupación] Resultados: " + resultados);

        assertThat(resultados).isNotEmpty();
        Map<String, Object> primeraOcupacion = resultados.get(0);
        assertThat(primeraOcupacion.get("idsesion")).isNotNull();
        assertThat(primeraOcupacion.get("porcentajeocupacion")).isNotNull();
    }
}