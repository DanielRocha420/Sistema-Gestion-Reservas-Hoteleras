package com.daniel.commons.dto.habitacion;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "DTO con la informacion detallada de respuesta de una habitacion")
public record HabitacionResponse(
        @Schema(description = "Identificador unico de la habitacion", example = "1")
        Long numHabitacion,

        @Schema(description = "Tipo de habitacion (nombre)", example = "INDIVIDUAL")
        String tipo,

        @Schema(description = "Tipo de habitacion (descripcion)", example = "Espacio asignado para un solo huésped.")
        String tipoDescripcion,

        @Schema(description = "Precio de la habitacion", example = "123.5")
        BigDecimal precio,

        @Schema(description = "Capacidad de huespedes", example = "3")
        Short capacidad,

        @Schema(description = "Estado que se encuentra la habitacion (nombre)", example = "OCUPADA")
        String estado,

        @Schema(description = "Estado que se encuentra la habitacion (descripcion)", example = "La habitacion se encuentra ocupada")
        String estadoDescripcion
) {
}
