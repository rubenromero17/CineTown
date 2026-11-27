package es.safareyes.cinetown.servicios;

import es.safareyes.cinetown.dto.CompraDTO;
import es.safareyes.cinetown.dto.CrearCompraDTO;
import es.safareyes.cinetown.mapper.CompraMapper;
import es.safareyes.cinetown.modelos.Compra;
import es.safareyes.cinetown.modelos.Sesion;
import es.safareyes.cinetown.modelos.Usuario;
import es.safareyes.cinetown.repositorios.ICompraRepository;
import es.safareyes.cinetown.repositorios.ISesionRepository;
import es.safareyes.cinetown.repositorios.IUsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompraService {

    private static final String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final int horasAntelacionCancelacion = 2;

    private final ICompraRepository compraRepository;
    private final ISesionRepository sesionRepository;
    private final IUsuarioRepository usuarioRepository;
    private final CompraMapper compraMapper;

    public Page<CompraDTO> listarPaginado(String email, Pageable pageable) {
        Page<Compra> pagina = (email != null && !email.isBlank())
                ? compraRepository.findByEmailIgnoreCase(email.trim(), pageable)
                : compraRepository.findAll(pageable);

        return pagina.map(this::mapearConPrecioUnitario);
    }

    public CompraDTO obtenerPorCodigo(String codigo) {
        Compra compra = compraRepository.findByCodigoIgnoreCase(codigo.trim())
                .orElseThrow(() -> new EntityNotFoundException("Compra no encontrada con código: " + codigo));
        return mapearConPrecioUnitario(compra);
    }

    @Transactional
    public CompraDTO comprar(CrearCompraDTO dto) {
        Sesion sesion = sesionRepository.findById(dto.getIdSesion())
                .orElseThrow(() -> new EntityNotFoundException("Sesión no encontrada con id: " + dto.getIdSesion()));

        if (!Boolean.TRUE.equals(sesion.getPelicula().getPublicada())) {
            throw new IllegalStateException("No se pueden comprar entradas para una película que no está en cartelera");
        }

        if (sesion.getAsientosDisponibles() == null || sesion.getAsientosDisponibles() < dto.getCantidad()) {
            throw new IllegalStateException("No hay suficientes asientos disponibles. Libres: " + sesion.getAsientosDisponibles());
        }

        Usuario usuario = null;
        String emailCompra = (dto.getEmail() != null && !dto.getEmail().isBlank()) ? dto.getEmail().trim() : null;

        if (dto.getIdUsuario() != null) {
            usuario = usuarioRepository.findById(dto.getIdUsuario())
                    .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con id: " + dto.getIdUsuario()));

            if (Boolean.FALSE.equals(usuario.getActivo())) {
                throw new IllegalStateException("El usuario está desactivado y no puede realizar compras");
            }

            if (emailCompra == null) {
                emailCompra = usuario.getEmail();
            }
        } else if (emailCompra == null) {
            throw new IllegalArgumentException("El email es obligatorio cuando se realiza la compra sin usuario registrado");
        }

        LocalDate fechaSesion = LocalDate.parse(sesion.getDia());
        double precioUnitario = calcularPrecioAplicado(sesion.getPrecioBase(), fechaSesion, sesion.getHoraSesion());
        double total = Math.round((precioUnitario * dto.getCantidad()) * 100.0) / 100.0;

        sesion.setAsientosDisponibles(sesion.getAsientosDisponibles() - dto.getCantidad());
        sesionRepository.save(sesion);

        Compra compra = Compra.builder()
                .usuario(usuario)
                .sesion(sesion)
                .codigo(generarCodigoUnico())
                .cantidad(dto.getCantidad())
                .total(total)
                .estado("comprada")
                .email(emailCompra)
                .fechaCompra(LocalDateTime.now().withNano(0))
                .build();

        Compra guardada = compraRepository.save(compra);

        CompraDTO respuesta = compraMapper.toDto(guardada);
        respuesta.setPrecioUnitario(precioUnitario);
        return respuesta;
    }

    @Transactional
    public CompraDTO cancelar(String codigo) {
        Compra compra = compraRepository.findByCodigoIgnoreCase(codigo.trim())
                .orElseThrow(() -> new EntityNotFoundException("Compra no encontrada con código: " + codigo));

        if (!"comprada".equalsIgnoreCase(compra.getEstado())) {
            throw new IllegalStateException("La compra con código " + compra.getCodigo() + " ya se encuentra cancelada");
        }

        Sesion sesion = compra.getSesion();
        LocalDateTime inicioSesion = LocalDateTime.of(LocalDate.parse(sesion.getDia()), sesion.getHoraSesion());

        if (LocalDateTime.now().isAfter(inicioSesion.minusHours(horasAntelacionCancelacion))) {
            throw new IllegalStateException("No se puede cancelar la compra con menos de "
                    + horasAntelacionCancelacion + " horas de antelación al inicio de la sesión");
        }

        int asientosActuales = sesion.getAsientosDisponibles() != null ? sesion.getAsientosDisponibles() : 0;
        int asientosRestaurados = asientosActuales + compra.getCantidad();
        if (sesion.getSala() != null && sesion.getSala().getCapacidad() != null) {
            asientosRestaurados = Math.min(asientosRestaurados, sesion.getSala().getCapacidad());
        }

        sesion.setAsientosDisponibles(asientosRestaurados);
        sesionRepository.save(sesion);

        compra.setEstado("cancelada");
        Compra cancelada = compraRepository.save(compra);

        return mapearConPrecioUnitario(cancelada);
    }

    private CompraDTO mapearConPrecioUnitario(Compra compra) {
        CompraDTO dto = compraMapper.toDto(compra);
        if (compra.getTotal() != null && compra.getCantidad() != null && compra.getCantidad() > 0) {
            dto.setPrecioUnitario(Math.round((compra.getTotal() / compra.getCantidad()) * 100.0) / 100.0);
        }
        return dto;
    }

    private String generarCodigoUnico() {
        String codigo;
        do {
            StringBuilder sb = new StringBuilder("CMP");
            for (int i = 0; i < 5; i++) {
                sb.append(caracteres.charAt(secureRandom.nextInt(caracteres.length())));
            }
            codigo = sb.toString();
        } while (compraRepository.existsByCodigo(codigo));
        return codigo;
    }

    private double calcularPrecioAplicado(double precioBase, LocalDate dia, LocalTime horaSesion) {
        DayOfWeek diaSemana = dia.getDayOfWeek();
        double precio = precioBase;

        if (diaSemana == DayOfWeek.WEDNESDAY) {
            precio = precioBase * 0.70;
        } else if (horaSesion.isBefore(LocalTime.of(14, 0))) {
            precio = precioBase * 0.80;
        } else if (diaSemana == DayOfWeek.FRIDAY || diaSemana == DayOfWeek.SATURDAY || diaSemana == DayOfWeek.SUNDAY) {
            precio = precioBase * 1.15;
        }

        return Math.round(precio * 100.0) / 100.0;
    }
}