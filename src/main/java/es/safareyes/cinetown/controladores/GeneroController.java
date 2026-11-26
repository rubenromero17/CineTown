package es.safareyes.cinetown.controladores;

import es.safareyes.cinetown.dto.GeneroConTotalPeliculasDTO;
import es.safareyes.cinetown.servicios.GeneroService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/generos")
@RequiredArgsConstructor
public class GeneroController {

    private final GeneroService generoService;

    @GetMapping
    public List<GeneroConTotalPeliculasDTO> listarConTotalPeliculas() {
        return generoService.listarConTotalPeliculas();
    }


}
