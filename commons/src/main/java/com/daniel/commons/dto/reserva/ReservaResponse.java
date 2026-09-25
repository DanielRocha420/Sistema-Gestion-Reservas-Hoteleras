package com.daniel.commons.dto.reserva;

import com.daniel.commons.enums.EstadoReserva;
import com.daniel.commons.enums.EstadoRegistro;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record ReservaResponse(

        @Schema(description = "Id del de la reserva")
        Long id,

        @Schema(description = "Estado de la reserva")
        EstadoReserva estadoReserva,

        @Schema(description = "Fecha de entrada de la reserva")
        LocalDateTime fecha_Entrada,

        @Schema(description = "Fecha de salida de la reserva")
        LocalDateTime fecha_Salida,

        @Schema(description = "Número de habitación de la reserva")
        Long numHabitacion,

        @Schema(description = "ID del huésped")
        Long idHuesped,

        @Schema(description = "Estado de registro")
        EstadoRegistro estadoRegistro
) {
}
