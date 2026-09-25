package com.reservashoteleras.reserva.entity;

import com.daniel.commons.enums.EstadoReserva;
import com.daniel.commons.enums.EstadoRegistro;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "RESERVA")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_RESERVA", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO_RESERVA", nullable = false, length = 50)
    private EstadoReserva estado;

    @Column(name = "FECHA_ENTRADA", nullable = false)
    private LocalDateTime fecha_Entrada;

    @Column(name = "FECHA_SALIDA", nullable = false)
    private LocalDateTime fecha_Salida;

    @Column(name = "NUM_HABITACION", nullable = false)
    private Long numHabitacion;

    @Column(name = "ID_HUESPED", nullable = false)
    private Long idHuesped;

    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO_REGISTRO", nullable = false, length = 20)
    private EstadoRegistro estadoRegistro;

    public void cambiarEstado(EstadoReserva nuevoEstado) {
        this.estado.validarCambio(nuevoEstado);
        this.estado = nuevoEstado;
    }

    public void setEstado(EstadoReserva estado) {
        this.estado = estado;
    }

    public void setNumHabitacion(Long numHabitacion) {
        this.numHabitacion = numHabitacion;
    }

    public void setIdHuesped(Long idHuesped) {
        this.idHuesped = idHuesped;
    }

    public void cambiarFechaEntrada(LocalDateTime fechaEntrada) {
        this.estado.validarModificacionFechaEntrada();
        this.fecha_Entrada = fechaEntrada;
    }

    public void cambiarFechaSalida(LocalDateTime fechaSalida) {
        this.estado.validarModificacionFechaSalida();
        this.fecha_Salida = fechaSalida;
    }

    public void cambiarFechas(LocalDateTime fechaEntrada, LocalDateTime fechaSalida) {
        this.estado.validarModificacionFechas();
        this.fecha_Entrada = fechaEntrada;
        this.fecha_Salida = fechaSalida;
    }

    public void cambiarHabitacion(Long numHabitacion) {
        this.estado.validarModificacionHabitacion();
        this.numHabitacion = numHabitacion;
    }

    public void checkIn() {
        this.estado.validarCambio(EstadoReserva.EN_CURSO);
        this.estado = EstadoReserva.EN_CURSO;
    }

    public void checkOut() {
        this.estado.validarCambio(EstadoReserva.FINALIZADA);
        this.estado = EstadoReserva.FINALIZADA;
    }

    public void cancelar() {
        this.estado.validarCambio(EstadoReserva.CANCELADA);
        this.estado = EstadoReserva.CANCELADA;
    }

    public void eliminar() {
        this.estadoRegistro = EstadoRegistro.ELIMINADO;
    }

    @PrePersist
    @PreUpdate
    private void validarFechas() {
        if (fecha_Entrada == null || fecha_Salida == null
                || !fecha_Salida.toLocalDate().isAfter(fecha_Entrada.toLocalDate())) {
            throw new IllegalArgumentException("La fecha de entrada debe ser anterior a la fecha de salida");
        }
        if (estadoRegistro == null) {
            estadoRegistro = EstadoRegistro.ACTIVO;
        }
    }
}