package com.daniel.commons.dto.reserva;

import com.daniel.commons.enums.EstadoReserva;
import io.swagger.v3.oas.annotations.media.Schema;
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
        Long idHabitacion,

        @Schema(description = "Estado de reserva. Al registrar siempre queda CONFIRMADA", example = "CONFIRMADA")
        EstadoReserva estadoReserva,

        @NotNull(message = "La fecha de entrada es requerida")
        @Schema(description = "Fecha representativa de entrada", example = "2026-10-20T00:00:00")
        LocalDateTime fechaEntrada,

        @NotNull(message = "La fecha de salida es requerida")
        @Schema(description = "Fecha representativa de salida", example = "2026-10-25T00:00:00")
        LocalDateTime fechaSalida
) {
}
