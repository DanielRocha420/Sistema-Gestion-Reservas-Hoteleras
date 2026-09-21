package com.daniel.commons.enums;

import com.daniel.commons.exceptions.RecursoNoEncontradoException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
@Getter
public enum EstadoHabitacion {
    DISPONIBLE(1L,"Habitacion Disponible"),
    OCUPADA(2L,"La habitacion se encuentra ocupada"),
    LIMPIEZA(3L, "La habitacion se encuentra en limpieza"),
    MANTENIMIENTO(4L,"La habitacion se encuentra en mantenimiento por el momento");

    private final Long codigo;
    private final String descripcion;

    public static EstadoHabitacion obtenerEstadoPorCodigo(Long codigo) {
        for (EstadoHabitacion h : values()) {
            if (Objects.equals(h.codigo, codigo))
                return h;
        }
        throw new RecursoNoEncontradoException("Codigo del estado no valido: " + codigo);
    }
}
