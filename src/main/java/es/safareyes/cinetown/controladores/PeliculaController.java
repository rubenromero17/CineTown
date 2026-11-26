package es.safareyes.cinetown.controladores;

import es.safareyes.cinetown.dto.DetallePeliculaDTO;
import es.safareyes.cinetown.dto.ListadoPeliculasDTO;
import es.safareyes.cinetown.servicios.PeliculaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/peliculas")
@RequiredArgsConstructor
public class PeliculaController {

    private final PeliculaService peliculaService;
    //Endpoint 1
    @GetMapping
    public Page<ListadoPeliculasDTO> listar(
            @RequestParam(required = false) Long idGenero,
            @RequestParam(required = false) String clasificacion,
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) Boolean publicada,
            @PageableDefault(size = 10, sort = "titulo") Pageable pageable) {

        return peliculaService.listar(idGenero, clasificacion, titulo, publicada, pageable);
    }
    //Endpoint 2
    @GetMapping("/{id}")
    public DetallePeliculaDTO obtenerPorId(@PathVariable Integer id) {
        return peliculaService.obtenerPorId(id);
    }
    //Endpoint 3
    @PostMapping
    public ResponseEntity<DetallePeliculaDTO> crear(@Valid @RequestBody DetallePeliculaDTO dto) {
        DetallePeliculaDTO creada = peliculaService.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }
    //Endpoint 4
    @PutMapping("/{id}")
    public DetallePeliculaDTO modificar(@PathVariable Integer id, @Valid @RequestBody DetallePeliculaDTO dto) {
        return peliculaService.modificar(id, dto);
    }

    //Endpoint 5
    @PatchMapping("/{id}/publicar")
    public DetallePeliculaDTO cambiarEstadoCartelera(
            @PathVariable Integer id,
            @RequestParam(required = false) Boolean publicada) {
        return peliculaService.cambiarEstadoCartelera(id, publicada);
    }


}