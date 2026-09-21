package com.reservashoteleras.habitaciones.entity;

import com.daniel.commons.enums.EstadoHabitacion;
import com.daniel.commons.enums.EstadoRegistro;
import com.daniel.commons.enums.TipoHabitacion;
import com.daniel.commons.utils.StringCustomUtils;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_HABITACION")
    private Long numHabitacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO")
    private TipoHabitacion tipo;

    @Column(name = "Precio", nullable = false)
    private BigDecimal precio;

    @Column(name = "CAPACIDAD")
    private Long capacidad;

    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO")
    private EstadoHabitacion estado;

    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO_REGISTRO", nullable = false)
    private EstadoRegistro estadoRegistro;

    private void validarDatos(TipoHabitacion tipo, BigDecimal precio, Long capacidad) {
        if (tipo == null) {
            throw new IllegalArgumentException("El tipo de habitación es requerido.");
        }

        ValoresNumericosUtils.validarBigDecimalPositivo(precio, "El precio de la habitación debe ser positivo.");
        ValoresNumericosUtils.validarLongPositivo(capacidad, "La capacidad debe ser mayor o igual a cero.");
    }



    private void validarNoOcupada() {
        if (this.estado == EstadoHabitacion.OCUPADA)
            throw new IllegalArgumentException("La habitacion no se puede elimniar");
    }
/*
    public void eliminar(){
       validarNoOcupada();

        this.

    }
*/


    public void actualizarTipoHabitacion(TipoHabitacion tipo){

    }
}
