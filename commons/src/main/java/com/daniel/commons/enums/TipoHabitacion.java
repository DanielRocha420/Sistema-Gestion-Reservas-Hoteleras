package com.daniel.commons.enums;

import com.daniel.commons.exceptions.RecursoNoEncontradoException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
@Getter
public enum TipoHabitacion {
    INDIVIDUAL(1L, "Espacio asignado para un solo huésped.", (short) 1, (short) 1),
    DOBLE(2L, "Habitación preparada para dos personas.", (short) 2, (short) 2),
    FAMILIAR(3L, "Cuenta con capacidad para cuatro o más personas, a veces con sofás cama o literas.", (short) 4, (short) 10);

    private final Long codigo;
    private final String descripcion;
    private final Short capacidadMinima;
    private final Short capacidadMaxima;

    public static TipoHabitacion obtenerTipoPorCodigo(Long codigo){
        for (TipoHabitacion t : values()) {
            if (Objects.equals(t.codigo, codigo))
                return t;
        }
        throw new RecursoNoEncontradoException("El codigo del tipo de habitacion no es valido: " + codigo);
    }

    public void validarCapacidad(Short capacidad) {
        if (capacidad == null) {
            throw new IllegalArgumentException("La capacidad es requerida.");
        }
        if (capacidad < capacidadMinima || capacidad > capacidadMaxima) {
            throw new IllegalArgumentException(String.format(
                "Para tipo %s la capacidad debe estar entre %d y %d",
                this.name(), capacidadMinima, capacidadMaxima
            ));
        }
    }
}
