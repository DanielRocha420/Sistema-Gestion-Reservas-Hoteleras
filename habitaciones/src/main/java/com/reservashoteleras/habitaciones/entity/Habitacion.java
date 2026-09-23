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

    private void validarDatos(TipoHabitacion tipo, BigDecimal precio, Short capacidad) {
        if (tipo == null) {
            throw new IllegalArgumentException("El tipo de habitación es requerido.");
        }

        ValoresNumericosUtils.validarBigDecimalPositivo(precio, "El precio de la habitación debe ser positivo.");
        ValoresNumericosUtils.validarRangoShort(capacidad,(short) 1,(short) 4, "La capacidad debe ser mayor o igual a cero.");
    }

    private void validarNoEliminado() {
        if (this.estadoRegistro == EstadoRegistro.ELIMINADO) {
            throw new IllegalArgumentException("La habitacion ya esta elimininada");
        }
    }

    private void validarNoOcupada() {
        if (this.estado == EstadoHabitacion.OCUPADA) {
            throw new IllegalArgumentException("La habitacion no se puede eliminar porque se encuentra ocupado ");
        }
    }

    public void cambiarEstado(EstadoHabitacion nuevoEstado){
        validarNoEliminado();
        if (nuevoEstado == null){
            throw new IllegalArgumentException("El nuevo estado es requerido");
        }

        if (this.estado == EstadoHabitacion.OCUPADA && nuevoEstado == EstadoHabitacion.DISPONIBLE){
            throw new IllegalArgumentException("No se puede actualizar ya que la habitacion se encuentra ocupada");
        }

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
            throw new IllegalArgumentException("El tipo de habitacion es requerido: " + tipo);
        }
        this.tipo = tipo;

    }
    public void actualizar(TipoHabitacion tipo, BigDecimal precio, Short capacidad) {
        validarNoEliminado();
        validarDatos(tipo, precio, capacidad);

        this.tipo = tipo;
        this.precio = precio;
        this.capacidad = capacidad;
    }


}
