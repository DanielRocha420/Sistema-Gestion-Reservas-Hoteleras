package com.daniel.commons.enums;

import com.daniel.commons.exceptions.RecursoNoEncontradoException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

@RequiredArgsConstructor
@Getter
public enum EstadoHabitacion {
    DISPONIBLE(1L, "Habitacion Disponible", true, true) {
        @Override
        public Set<EstadoHabitacion> puedeCambiar() {
            return EnumSet.of(OCUPADA, MANTENIMIENTO);
        }
    },
    OCUPADA(2L, "La habitacion se encuentra ocupada", true, false) {
        @Override
        public Set<EstadoHabitacion> puedeCambiar() {
            return EnumSet.of(LIMPIEZA);
        }
    },
    LIMPIEZA(3L, "La habitacion se encuentra en limpieza", true, false) {
        @Override
        public Set<EstadoHabitacion> puedeCambiar() {
            return EnumSet.of(DISPONIBLE, MANTENIMIENTO);
        }
    },
    MANTENIMIENTO(4L, "La habitacion se encuentra en mantenimiento por el momento", true, false) {
        @Override
        public Set<EstadoHabitacion> puedeCambiar() {
            return EnumSet.of(DISPONIBLE);
        }
    };

    private final Long codigo;
    private final String descripcion;
    private final boolean actualizable;
    private final boolean eliminable;

    public abstract Set<EstadoHabitacion> puedeCambiar();

    public boolean puedeCambiarA(EstadoHabitacion nuevoEstado) {
        return puedeCambiar().contains(nuevoEstado);
    }

    public static EstadoHabitacion obtenerEstadoPorCodigo(Long codigo) {
        for (EstadoHabitacion h : values()) {
            if (Objects.equals(h.codigo, codigo))
                return h;
        }
        throw new RecursoNoEncontradoException("Codigo del estado no valido: " + codigo);
    }
}
