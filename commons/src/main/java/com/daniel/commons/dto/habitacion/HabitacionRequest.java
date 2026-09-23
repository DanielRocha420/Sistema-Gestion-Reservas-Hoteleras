package com.daniel.commons.dto.habitacion;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@Schema(description = "DTO con la informacion para registrar o actualizar una habitacion")
public record HabitacionRequest(

        @Schema(description = "Numero de habitacion", example = "101")
        @NotNull(message = "El número de habitación es requerido")
        @Positive(message = "El número de habitación debe ser mayor a 0")
        Long numHabitacion,

        @Schema(description = "Tipo de la habitacion", example = "INDIVIDUAL")
        @NotBlank(message = "El tipo de habitación es requerido")
        String tipo,

        @Schema(description = "Precio de la habitacion", example = "123.5")
        @NotNull(message = "El precio es requerido")
        @DecimalMin(value = "0.01", message = "El precio debe ser mayor a $0.01")
        @DecimalMax(value = "100000.0", message = "El precio máximo es $100,000.00")
        BigDecimal precio,

        @Schema(description = "Capacidad de la habitacion (minimo 1) (maximo 10)", example = "3",minimum = "1", maximum = "10")
        @NotNull(message = "La capacidad es requerida")
        @Min(value = 1, message = "La capacidad mínima es 1 huésped")
        @Max(value = 10, message = "La capacidad máxima es 10 huéspedes")
        Short capacidad
) {
}
