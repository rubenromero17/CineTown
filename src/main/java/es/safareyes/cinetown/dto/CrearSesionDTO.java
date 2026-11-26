package es.safareyes.cinetown.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CrearSesionDTO {

    @NotNull(message = "El id de la película es obligatorio")
    private Integer idPelicula;

    @NotNull(message = "El id de la sala es obligatorio")
    private Integer idSala;

    @NotNull(message = "El día de la sesión es obligatorio")
    @FutureOrPresent(message = "La fecha de la sesión no puede ser anterior a hoy")
    private LocalDate dia;

    @NotNull(message = "La hora de inicio es obligatoria")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime horaSesion;

    @NotNull(message = "El precio base es obligatorio")
    @Positive(message = "El precio base debe ser mayor que 0")
    private Double precioBase;
}