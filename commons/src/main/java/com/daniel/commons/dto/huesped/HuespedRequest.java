package com.daniel.commons.dto.huesped;

import com.daniel.commons.enums.EstadoReserva;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record HuespedRequest(
        @NotBlank(message = "El nombre es requerido")
        @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
        @Schema(description = "Nombre del Huesped", example = "Nayely")
        String nombre,

        @NotBlank(message = "El apellido paterno es requerido")
        @Size(min = 2, max = 50, message = "El apellido paterno debe tener entre 2 y 50 caracteres")
        @Schema(description = "Apellido paterno del huesped", example = "Hernandez")
        String apellidoPaterno,

        @NotBlank(message = "El apellido materno es requerido")
        @Schema(description = "Apellido materno del huesped", example = "Silva")
        @Size(min = 2, max = 50, message = "El apellido materno debe tener entre 2 y 50 caracteres")
        String apellidoMaterno,

        @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", message = "El correo debe tener un dominio válido")
        @Email(message = "El formato del correo electronico no es valido")
        @Schema(description = "Email unico del huesped", example = "naye20_97@gmail.com")
        String email,

        @NotBlank(message = "El telefono es requerido")
        @Schema(description = "Telefono unico del huesped", example = "7352713050")
        String telefono,

        @Schema(description = "Tipo de documento de identificacion", example = "INE")
        String tipoDocumento,

        @NotBlank(message = "La documentacion es requerida")
        @Schema(description = "Numero de identificacion unico", example = "JWIJED3435OP")
        String documento,

        @NotBlank(message = "La nacionalidad es requerida")
        @Schema(description = "Nacionalidad del huesped", example = "MEXICANA")
        String nacionalidad,

        @Schema(description = "El estado de la reserva en la que el huesped se encuentra", example = "CONFIRMADA")
        EstadoReserva estado
) {
}
