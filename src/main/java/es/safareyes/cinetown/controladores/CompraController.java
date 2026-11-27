package es.safareyes.cinetown.controladores;

import es.safareyes.cinetown.dto.CompraDTO;
import es.safareyes.cinetown.dto.CrearCompraDTO;
import es.safareyes.cinetown.servicios.CompraService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/compras")
@RequiredArgsConstructor
public class CompraController {

    private final CompraService compraService;

    @PostMapping
    public ResponseEntity<CompraDTO> comprar(@Valid @RequestBody CrearCompraDTO dto) {
        CompraDTO creada = compraService.comprar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @GetMapping
    public ResponseEntity<Page<CompraDTO>> listar(
            @RequestParam(required = false) String email,
            @PageableDefault(size = 10, sort = "fechaCompra", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(compraService.listarPaginado(email, pageable));
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<CompraDTO> obtenerPorCodigo(@PathVariable String codigo) {
        return ResponseEntity.ok(compraService.obtenerPorCodigo(codigo));
    }

    @PatchMapping("/{codigo}/cancelar")
    public ResponseEntity<CompraDTO> cancelar(@PathVariable String codigo) {
        return ResponseEntity.ok(compraService.cancelar(codigo));
    }
}