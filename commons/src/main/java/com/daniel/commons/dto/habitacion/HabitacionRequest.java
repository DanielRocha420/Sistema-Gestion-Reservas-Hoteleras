package com.daniel.commons.dto.habitacion;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@Schema(description = "DTO con la informacion para registrar o actualizar una habitacion")
public record HabitacionRequest(

        @Schema(description = "Numero de habitacion", example = "101")
        @NotNull(message = "El numero de habitacion es requerido")
        @Positive(message = "El numero de habitacion debe ser positivo")
        Long numHabitacion,

        @Schema(description = "Tipo de la habitacion", example = "INDIVIDUAL", maxLength = 50)
        @NotBlank(message = "El tipo de habitacion es requerido")
        @Size(min = 1, max = 50, message = "El tipo de habitacion debe tener entre 1 y 50 caracteres")
        String tipo,

        @Schema(description = "Precio de la habitacion", example = "123.5")
        @NotNull(message = "El precio es requerido")
        @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
        @DecimalMax(value = "100000.0", message = "El precio máximo es 100000.0")
        BigDecimal precio,

        @Schema(description = "Capacidad de la habitacion (minimo 1) (maximo 4)", example = "3",minimum = "1", maximum = "4")
        @NotNull(message = "La capacidad es requerida")
        @Min(value = 1, message = "La capacidad minima es de 1 huesped")
        @Max(value = 4, message = "La capacidad maxima es de 4 huespedes")
        Short capacidad
) {
}
