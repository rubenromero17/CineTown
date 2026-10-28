package es.safareyes.cinetown.servicios;

import es.safareyes.cinetown.dto.RecaudacionDTO;
import es.safareyes.cinetown.dto.request.PeliculaRequest;
import es.safareyes.cinetown.dto.response.PeliculaResponse;
import es.safareyes.cinetown.dto.response.RecaudacionPeliculaResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PeliculaService {
    Page<PeliculaResponse> listar(String titulo, String genero, Boolean enCartelera, Pageable pageable);
    PeliculaResponse buscarPorId(Integer id);
    RecaudacionDTO obtenerRecaudacion(Integer id);
}
