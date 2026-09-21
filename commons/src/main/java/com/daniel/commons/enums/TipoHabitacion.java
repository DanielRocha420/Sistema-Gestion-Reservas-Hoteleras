package com.daniel.commons.enums;

import com.daniel.commons.exceptions.RecursoNoEncontradoException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
@Getter
public enum TipoHabitacion {
    INDIVIDUAL(1L, "Espacio asignado para un solo huésped."),
    DOBLE(2L,"Habitación preparada para dos personas."),
    FAMILIAR(3L,"Cuenta con capacidad para cuatro o más personas, a veces con sofás cama o literas.");

    private final Long codigo;
    private final String descripcion;

    public static TipoHabitacion obtenerTipoPorCodigo(Long codigo){
        for (TipoHabitacion t : values()) {
            if (Objects.equals(t.codigo, codigo))
                return t;
        }
        throw new RecursoNoEncontradoException("El codigo del tipo de avitacion no es valido: " + codigo);
    }
}
