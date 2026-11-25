package es.safareyes.cinetown.servicios;

import es.safareyes.cinetown.FIltrosDinamicos.PeliculaSpecification;
import es.safareyes.cinetown.dto.DetallePeliculaDTO;
import es.safareyes.cinetown.dto.GeneroDTO;
import es.safareyes.cinetown.dto.ListadoPeliculasDTO;
import es.safareyes.cinetown.mapper.PeliculaMapper;
import es.safareyes.cinetown.modelos.Genero;
import es.safareyes.cinetown.modelos.Pelicula;
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
    private final PeliculaMapper peliculaMapper;

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
}