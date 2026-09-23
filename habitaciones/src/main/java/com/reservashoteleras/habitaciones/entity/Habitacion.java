package com.reservashoteleras.habitaciones.entity;

import com.daniel.commons.enums.EstadoHabitacion;
import com.daniel.commons.enums.EstadoRegistro;
import com.daniel.commons.enums.TipoHabitacion;
import com.daniel.commons.utils.ValoresNumericosUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "HABITACION")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Habitacion {
    @Id
    @Column(name = "ID_HABITACION")
    private Long numHabitacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO")
    private TipoHabitacion tipo;

    @Column(name = "Precio", nullable = false)
    private BigDecimal precio;

    @Column(name = "CAPACIDAD")
    private Short capacidad;

    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO")
    private EstadoHabitacion estado = EstadoHabitacion.DISPONIBLE;

    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO_REGISTRO", nullable = false)
    private EstadoRegistro estadoRegistro;

    public void cambiarEstado(EstadoHabitacion nuevoEstado){
        validarNoEliminado();
        this.estado.puedeCambiarA(nuevoEstado);
        this.estado = nuevoEstado;
    }

    public void liberar() {
        validarNoEliminado();
        this.estado = EstadoHabitacion.DISPONIBLE;
    }

    public void eliminar(){
        validarNoEliminado();
        validarNoOcupada();
        this.estadoRegistro = EstadoRegistro.ELIMINADO;
    }

    public void actualizarTipoHabitacion(TipoHabitacion tipo){
        validarNoEliminado();
        if (tipo == null){
            throw new IllegalArgumentException("El tipo de habitacion es requerido");
        }
        tipo.validarCapacidad(this.capacidad);
        this.tipo = tipo;
    }

    public void actualizar(TipoHabitacion tipo, BigDecimal precio, Short capacidad) {
        validarNoEliminado();
        validarDatosGenerales(tipo, precio);
        if (tipo != null) {
            tipo.validarCapacidad(capacidad);
        }

        this.tipo = tipo;
        this.precio = precio;
        this.capacidad = capacidad;
    }

    private void validarDatosGenerales(TipoHabitacion tipo, BigDecimal precio) {
        if (tipo == null) {
            throw new IllegalArgumentException("El tipo de habitación es requerido.");
        }
        ValoresNumericosUtils.validarBigDecimalPositivo(precio, "El precio de la habitación debe ser positivo.");
    }

    private void validarNoEliminado() {
        if (this.estadoRegistro == EstadoRegistro.ELIMINADO) {
            throw new IllegalArgumentException("La habitacion ya esta eliminada");
        }
    }

    private void validarNoOcupada() {
        if (this.estado == EstadoHabitacion.OCUPADA) {
            throw new IllegalArgumentException("La habitacion no se puede eliminar porque se encuentra ocupada");
        }
    }
}
