package es.safareyes.cinetown.controladores;

import es.safareyes.cinetown.dto.SalaDTO;
import es.safareyes.cinetown.servicios.SalaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/salas")
@RequiredArgsConstructor
public class SalaController {
    private final SalaService salaService;

    @GetMapping
    public List<SalaDTO> listar() {
        return salaService.listar();
    }
}
