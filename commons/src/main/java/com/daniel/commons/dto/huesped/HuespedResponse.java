package com.daniel.commons.dto.huesped;

import com.daniel.commons.enums.EstadoRegistro;
import io.swagger.v3.oas.annotations.media.Schema;

public record HuespedResponse(
        @Schema(description = "Identificador unico del huesped del hotel", example = "1")
        Long id,

        @Schema(description = "Nombre del huesped", example = "Daniel")
        String nombre,

        @Schema(description = "Apellido paterno", example = "Hernandez")
        String apellidoPaterno,

        @Schema(description = "Apellido msterno", example = "Rocha")
        String apellidoMaterno,

        @Schema(description = "Correo electronico del cliente", example = "daniel_Roche@gmail.com")
        String email,

        @Schema(description = "Telefono unico del cliente", example = "7729342432")
        String telefono,

        @Schema(description = "Tipo de documento que presenta el huesped", example = "LICENCIA_CONDUCIR")
        String tipoDocumento,

        @Schema(description = "Clave unica del huesped", example = "242362412652")
        String documento,

        @Schema(description = "Nacionalidad del huesped", example = "ESPAÑOLA")
        String nacionalidad,

        @Schema(description = "Estado del registro del huesped", example = "ACTIVO")
        EstadoRegistro estado
) {
}
