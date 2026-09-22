package com.reservashoteleras.habitaciones.mapper;

import com.daniel.commons.dto.habitacion.HabitacionRequest;
import com.daniel.commons.dto.habitacion.HabitacionResponse;
import com.daniel.commons.enums.EstadoHabitacion;
import com.daniel.commons.enums.EstadoRegistro;
import com.daniel.commons.enums.TipoHabitacion;
import com.daniel.commons.mapper.CommonMapper;
import com.daniel.commons.utils.ValoresNumericosUtils;
import com.reservashoteleras.habitaciones.entity.Habitacion;
import org.springframework.stereotype.Component;

@Component
public class HabitacionMapper implements CommonMapper<HabitacionRequest, HabitacionResponse, Habitacion> {

    @Override
    public Habitacion requestAEntidad(HabitacionRequest request){
        if (request == null) return null;

        ValoresNumericosUtils.validarLongPositivo(request.numHabitacion(), "El número de habitación debe ser mayor a 0.");

        TipoHabitacion tipo = TipoHabitacion.valueOf(request.tipo().toUpperCase());

        return Habitacion.builder()
                .numHabitacion(request.numHabitacion())
                .tipo(tipo)
                .precio(request.precio())
                .capacidad(request.capacidad())
                .estado(EstadoHabitacion.DISPONIBLE)
                .estadoRegistro(EstadoRegistro.ACTIVO)
                .build();
    }

    @Override
    public HabitacionResponse entidadAResponse(Habitacion entidad){
        if (entidad == null) return null;

        return new HabitacionResponse(
                entidad.getNumHabitacion(),
                entidad.getTipo().name(),
                entidad.getTipo().getDescripcion(),
                entidad.getPrecio(),
                entidad.getCapacidad(),
                entidad.getEstado().name(),
                entidad.getEstado().getDescripcion()
        );

    }
}
