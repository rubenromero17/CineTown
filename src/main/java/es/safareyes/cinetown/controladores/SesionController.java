package es.safareyes.cinetown.controladores;

import es.safareyes.cinetown.dto.CrearSesionDTO;
import es.safareyes.cinetown.dto.SesionDTO;
import es.safareyes.cinetown.servicios.SesionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/sesiones")
@RequiredArgsConstructor
public class SesionController {
    private final SesionService sesionService;

    @PostMapping
    public ResponseEntity<SesionDTO> programar(@Valid @RequestBody CrearSesionDTO dto) {
        SesionDTO creada = sesionService.programar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }
}
