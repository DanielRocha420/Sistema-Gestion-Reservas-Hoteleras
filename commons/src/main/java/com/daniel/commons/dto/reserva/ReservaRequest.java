package com.daniel.commons.dto.reserva;

import com.daniel.commons.enums.EstadoReserva;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record ReservaRequest(

        @NotNull(message = "El huesped es requerido")
        @Positive(message = "El id del huesped debe ser positivo")
        @Schema(description = "Id del huesped que reserva", example = "1")
        Long idHuesped,

        @NotNull(message = "La habitacion es requerida")
        @Positive(message = "El numero de habitacion debe ser positivo")
        @Schema(description = "Numero de la habitacion a reservar", example = "1")
        Long numHabitacion,

        @NotNull(message = "El estado de la reserva es requerido")
        @Schema(description = "Estado de reserva", example = "EN_CURSO")
        EstadoReserva estadoReserva,

        @NotNull(message = "La fecha de entrada es requerida")
        @Schema(description = "Fecha de entrada de la reserva", example = "20/10/26")
        LocalDateTime fechaEntrada,

        @NotNull(message = "La fecha de salida es requerida")
        @Schema(description = "Fecha de salida de la reserva", example = "25/10/26")
        LocalDateTime fechaSalida
) {
}
