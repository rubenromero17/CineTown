package es.safareyes.cinetown.seguridad;

import lombok.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;

@Service
public class JwtService {
    private final JwtEncoder codificador;
    private final JwtDecoder decodificador;
    private final long minutos;
    private final String emisor;

    public JwtService(
            @Value("${jwt.secreto}") String secreto,
            @Value("${jwt.minutos:60}") long minutos,
            @Value("${jwt.emisor:cinetown}") String emisor) {
        SecretKey clave = new SecretKeySpec(secreto.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        this.codificador = NimbusJwtEncoder.withSecretKey(clave)
                .algorithm(MacAlgorithm.HS256)
                .build();
        this.decodificador = NimbusJwtDecoder.withSecretKey(clave)
                .macAlgorithm(MacAlgorithm.HS256)
                .build();
        this.minutos = minutos;
        this.emisor = emisor;
    }
    }
public String generar(UsuarioDetails detalles) {
    Instant ahora = Instant.now();
    JwtClaimsSet contenido = JwtClaimsSet.builder()
            .issuer(emisor)
            .issuedAt(ahora)
            .expiresAt(ahora.plus(minutos, ChronoUnit.MINUTES))
            .subject(detalles.getUsername())
            .claim("rol", detalles.usuario().getRol().name())
            .claim("nombre", detalles.usuario().getNombre())
            .build();

    JwsHeader cabecera = JwsHeader.with(MacAlgorithm.HS256).build();
    return codificador.encode(JwtEncoderParameters.from(cabecera, contenido)).getTokenValue();
}

public Jwt validar(String token) {
    return decodificador.decode(token);
}

public long getSegundosDeVida() {
    return minutos * 60;
}
}
