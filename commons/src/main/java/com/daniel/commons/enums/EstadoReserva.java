package com.daniel.commons.enums;

import com.daniel.commons.exceptions.RecursoNoEncontradoException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

@RequiredArgsConstructor
@Getter
public enum EstadoReserva {
    CONFIRMADA(1L, "Confirmada por el paciente", true, true) {
        @Override
        public Set<EstadoReserva> puedeCambiar() {
            return EnumSet.of(EN_CURSO, CANCELADA);
        }

        @Override
        public boolean puedeModificarFechas() {
            return true;
        }

        @Override
        public boolean puedeModificarFechaEntrada() {
            return true;
        }

        @Override
        public boolean puedeModificarFechaSalida() {
            return true;
        }

        @Override
        public boolean puedeModificarHabitacion() {
            return true;
        }
    },
    EN_CURSO(2L, "Paciente llego a su cita", true, false) {
        @Override
        public Set<EstadoReserva> puedeCambiar() {
            return EnumSet.of(FINALIZADA);
        }

        @Override
        public boolean puedeModificarFechas() {
            return true;
        }

        @Override
        public boolean puedeModificarFechaEntrada() {
            return false;
        }

        @Override
        public boolean puedeModificarFechaSalida() {
            return true;
        }

        @Override
        public boolean puedeModificarHabitacion() {
            return false;
        }
    },
    FINALIZADA(3L, "Cita Finalizada", false, true) {
        @Override
        public Set<EstadoReserva> puedeCambiar() {
            return Set.of();
        }

        @Override
        public boolean puedeModificarFechas() {
            return false;
        }

        @Override
        public boolean puedeModificarFechaEntrada() {
            return false;
        }

        @Override
        public boolean puedeModificarFechaSalida() {
            return false;
        }

        @Override
        public boolean puedeModificarHabitacion() {
            return false;
        }
    },
    CANCELADA(4L, "Cita cancelada", false, true) {
        @Override
        public Set<EstadoReserva> puedeCambiar() {
            return Set.of();
        }

        @Override
        public boolean puedeModificarFechas() {
            return false;
        }

        @Override
        public boolean puedeModificarFechaEntrada() {
            return false;
        }

        @Override
        public boolean puedeModificarFechaSalida() {
            return false;
        }

        @Override
        public boolean puedeModificarHabitacion() {
            return false;
        }
    };

    private final Long codigo;
    private final String descripcion;
    private final boolean actualizable;
    private final boolean modificable;

    public abstract Set<EstadoReserva> puedeCambiar();

    public abstract boolean puedeModificarFechas();

    public abstract boolean puedeModificarFechaEntrada();

    public abstract boolean puedeModificarFechaSalida();

    public abstract boolean puedeModificarHabitacion();

    public boolean puedeCambiarA(EstadoReserva nuevoEstado) {
        return puedeCambiar().contains(nuevoEstado);
    }

    public void validarCambio(EstadoReserva nuevoEstado) {
        if (!puedeCambiarA(nuevoEstado)) {
            throw new IllegalArgumentException(
                String.format("No se puede cambiar de %s a %s. Transición no permitida.", this.name(), nuevoEstado.name())
            );
        }
    }

    public void validarModificacionFechaEntrada() {
        if (!puedeModificarFechaEntrada()) {
            throw new IllegalArgumentException(
                String.format("No se puede modificar la fecha de entrada en estado %s", this.name())
            );
        }
    }

    public void validarModificacionFechaSalida() {
        if (!puedeModificarFechaSalida()) {
            throw new IllegalArgumentException(
                String.format("No se puede modificar la fecha de salida en estado %s", this.name())
            );
        }
    }

    public void validarModificacionFechas() {
        if (!puedeModificarFechas()) {
            throw new IllegalArgumentException(
                String.format("No se pueden modificar fechas en estado %s", this.name())
            );
        }
    }

    public void validarModificacionHabitacion() {
        if (!puedeModificarHabitacion()) {
            throw new IllegalArgumentException(
                String.format("No se puede modificar la habitación en estado %s", this.name())
            );
        }
    }

    public static EstadoReserva obtenerEstadoPorCodigo(Long codigo) {
        for (EstadoReserva e : values()) {
            if (Objects.equals(e.codigo, codigo)) {
                return e;
            }
        }
        throw new RecursoNoEncontradoException("Codigo de reserva no valido: " + codigo);
    }
}
