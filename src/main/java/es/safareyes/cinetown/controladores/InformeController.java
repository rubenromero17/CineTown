package es.safareyes.cinetown.controladores;

import es.safareyes.cinetown.dto.OcupacionSesionDTO;
import es.safareyes.cinetown.servicios.InformeService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/informes")
@RequiredArgsConstructor
public class InformeController {

    private final InformeService informeService;

    @GetMapping("/ocupacion")
    public ResponseEntity<List<OcupacionSesionDTO>> obtenerOcupacion(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ResponseEntity.ok(informeService.obtenerOcupacionPorFecha(fecha));
    }
}