package es.safareyes.cinetown.servicios;

import es.safareyes.cinetown.FIltrosDinamicos.PeliculaSpecification;
import es.safareyes.cinetown.dto.DetallePeliculaDTO;
import es.safareyes.cinetown.dto.GeneroDTO;
import es.safareyes.cinetown.dto.ListadoPeliculasDTO;
import es.safareyes.cinetown.dto.RecaudacionDTO;
import es.safareyes.cinetown.mapper.PeliculaMapper;
import es.safareyes.cinetown.modelos.Genero;
import es.safareyes.cinetown.modelos.Pelicula;
import es.safareyes.cinetown.repositorios.ICompraRepository;
import es.safareyes.cinetown.repositorios.IGeneroRepository;
import es.safareyes.cinetown.repositorios.IPeliculaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PeliculaService {

    private final IPeliculaRepository peliculaRepository;
    private final IGeneroRepository generoRepository;
    private final ICompraRepository compraRepository;
    private final PeliculaMapper peliculaMapper;

    //Endpoint 1: Listar películas con filtros dinámicos
    public Page<ListadoPeliculasDTO> listar(
            Long idGenero,
            String clasificacion,
            String titulo,
            Boolean publicada,
            Pageable pageable) {

        Specification<Pelicula> spec = Specification
                .where(PeliculaSpecification.conGenero(idGenero))
                .and(PeliculaSpecification.conClasificacion(clasificacion))
                .and(PeliculaSpecification.conTitulo(titulo))
                .and(PeliculaSpecification.estaPublicada(publicada));

        return peliculaRepository.findAll(spec, pageable)
                .map(peliculaMapper::toListadoDto);
    }

    public DetallePeliculaDTO obtenerPorId(Integer id) {
        Pelicula pelicula = peliculaRepository.findWithGenerosByIdPelicula(id)
                .orElseThrow(() -> new EntityNotFoundException("Película no encontrada con id: " + id));
        return peliculaMapper.toDetalleDto(pelicula);
    }

    //Endpoint 3: Crear película
    @Transactional
    public DetallePeliculaDTO crear(DetallePeliculaDTO dto) {
        if (peliculaRepository.existsByTituloIgnoreCase(dto.getTitulo().trim())) {
            throw new IllegalStateException("Ya existe una película con el título: " + dto.getTitulo());
        }

        Set<Integer> idsGeneros = dto.getGeneros().stream()
                .map(GeneroDTO::getId)
                .collect(Collectors.toSet());

        List<Genero> generosEncontrados = generoRepository.findAllById(idsGeneros);
        if (generosEncontrados.size() != idsGeneros.size()) {
            throw new EntityNotFoundException("Uno o más géneros indicados no existen");
        }

        Pelicula pelicula = peliculaMapper.toEntity(dto);
        pelicula.setTitulo(dto.getTitulo().trim());
        pelicula.setPublicada(dto.getPublicada() != null ? dto.getPublicada() : true);
        pelicula.setGeneros(new HashSet<>(generosEncontrados));

        Pelicula guardada = peliculaRepository.save(pelicula);
        return peliculaMapper.toDetalleDto(guardada);
    }

    //Endpoint 4: Modificar película
    @Transactional
    public DetallePeliculaDTO modificar(Integer id, DetallePeliculaDTO dto) {
        Pelicula pelicula = peliculaRepository.findWithGenerosByIdPelicula(id)
                .orElseThrow(() -> new EntityNotFoundException("Película no encontrada con id: " + id));

        if (peliculaRepository.existsByTituloIgnoreCaseAndIdNot(dto.getTitulo().trim(), id)) {
            throw new IllegalStateException("Ya existe otra película con el título: " + dto.getTitulo());
        }

        Set<Integer> idsGeneros = dto.getGeneros().stream()
                .map(GeneroDTO::getId)
                .collect(Collectors.toSet());

        List<Genero> generosEncontrados = generoRepository.findAllById(idsGeneros);
        if (generosEncontrados.size() != idsGeneros.size()) {
            throw new EntityNotFoundException("Los generos indicados no existen");
        }
        pelicula.setTitulo(dto.getTitulo().trim());
        pelicula.setSinopsis(dto.getSinopsis());
        pelicula.setDuracion(dto.getDuracion());
        pelicula.setAnio(dto.getAnio());
        pelicula.setClasificacionEdad(dto.getClasificacionEdad());
        pelicula.setPublicada(dto.getPublicada());
        pelicula.setGeneros(new HashSet<>(generosEncontrados));

        Pelicula actualizada = peliculaRepository.save(pelicula);
        return peliculaMapper.toDetalleDto(actualizada);
    }

    //Endpoint 5: Cambiar estado de publicación de la película
    @Transactional
    public DetallePeliculaDTO cambiarEstadoCartelera(Integer id, Boolean publicada) {
        Pelicula pelicula = peliculaRepository.findWithGenerosByIdPelicula(id)
                .orElseThrow(() -> new EntityNotFoundException("Película no encontrada con id: " + id));

        boolean nuevoEstado = (publicada != null) ? publicada : !Boolean.TRUE.equals(pelicula.getPublicada());
        pelicula.setPublicada(nuevoEstado);

        Pelicula actualizada = peliculaRepository.save(pelicula);
        return peliculaMapper.toDetalleDto(actualizada);
    }

    //Endpoint 6: Entradas vendidas y recaudación total de una película
    public RecaudacionDTO obtenerRecaudacion(Integer id) {
        Pelicula pelicula = peliculaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Película no encontrada con id: " + id));
        return compraRepository.obtenerRecaudacionPelicula(id)
                .map(rec -> {
                    if (rec.getRecaudacionTotal() != null) {
                        rec.setRecaudacionTotal(Math.round(rec.getRecaudacionTotal() * 100.0) / 100.0);
                    }
                    return rec;
                })
                .orElseGet(() -> RecaudacionDTO.builder()
                        .idPelicula(pelicula.getId())
                        .titulo(pelicula.getTitulo())
                        .entradasVendidas(0L)
                        .recaudacionTotal(0.0)
                        .build());
    }
}