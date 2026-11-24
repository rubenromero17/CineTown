package es.safareyes.cinetown.controladores;

import es.safareyes.cinetown.dto.RecaudacionDTO;
import es.safareyes.cinetown.dto.response.PeliculaResponse;
import es.safareyes.cinetown.servicios.PeliculaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/peliculas")
@RequiredArgsConstructor
public class PeliculaController {
    private final PeliculaService peliculaService;

    @GetMapping("/peliculas")
    public Page<PeliculaResponse> listar(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) String genero,
            @RequestParam(required = false) Boolean enCartelera,
            @PageableDefault(size = 10, sort = "titulo") Pageable pageable) {
        return peliculaService.listar(titulo, genero, enCartelera, pageable);
    }

    @GetMapping("/{id}")
    public PeliculaResponse buscarPorId(@PathVariable Integer id) {
        return peliculaService.buscarPorId(id);
    }

    @GetMapping("/{id}/recaudacion")
    public RecaudacionDTO obtenerRecaudacion(@PathVariable Integer id) {
        return peliculaService.obtenerRecaudacion(id);
    }
}
