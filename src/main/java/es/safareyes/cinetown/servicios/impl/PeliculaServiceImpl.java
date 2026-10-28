package es.safareyes.cinetown.servicios.impl;

import es.safareyes.cinetown.dto.RecaudacionDTO;
import es.safareyes.cinetown.dto.response.PeliculaResponse;

import es.safareyes.cinetown.exepciones.ElementoNoEncontradoException;
import es.safareyes.cinetown.mapper.PeliculaMapper;
import es.safareyes.cinetown.modelos.Pelicula;
import es.safareyes.cinetown.repositorios.ICompraRepository;
import es.safareyes.cinetown.repositorios.IPeliculaRepository;
import es.safareyes.cinetown.servicios.PeliculaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class PeliculaServiceImpl implements PeliculaService {

    private final IPeliculaRepository peliculaRepository;
    private final ICompraRepository compraRepository;
    private final PeliculaMapper peliculaMapper;

    @Override
    public Page<PeliculaResponse> listar(String titulo, String genero, Boolean enCartelera, Pageable pageable) {
        return peliculaRepository.buscarConFiltros(titulo, genero, enCartelera, pageable)
                .map(peliculaMapper::aResponse);
    }

    @Override
    public PeliculaResponse buscarPorId(Integer id) {
        Pelicula pelicula = peliculaRepository.findById(id)
                .orElseThrow(() -> new ElementoNoEncontradoException("No existe la película con ID " + id));
        return peliculaMapper.aResponse(pelicula);
    }

    @Override
    public RecaudacionDTO obtenerRecaudacion(Integer id) {
        Pelicula pelicula = peliculaRepository.findById(id)
                .orElseThrow(() -> new ElementoNoEncontradoException("No existe la película con ID " + id));

        return compraRepository.obtenerRecaudacionPelicula(id)
                .orElse(new RecaudacionDTO(pelicula.getId(), pelicula.getTitulo(), 0L, BigDecimal.ZERO));
    }
}