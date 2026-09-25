package com.reservashoteleras.reserva.mapper;

import com.daniel.commons.dto.reserva.ReservaRequest;
import com.daniel.commons.dto.reserva.ReservaResponse;
import com.daniel.commons.mapper.CommonMapper;
import com.reservashoteleras.reserva.entity.Reserva;
import org.springframework.stereotype.Component;

@Component
public class ReservaMapper implements CommonMapper<ReservaRequest, ReservaResponse, Reserva> {

    @Override
    public Reserva requestAEntidad(ReservaRequest request) {
        if (request == null) return null;
        return Reserva.builder()
                .estado(request.estadoReserva())
                .fecha_Entrada(request.fechaEntrada())
                .fecha_Salida(request.fechaSalida())
                .numHabitacion(request.numHabitacion())
                .idHuesped(request.idHuesped())
                .build();
    }

    @Override
    public ReservaResponse entidadAResponse(Reserva entidad) {
        if (entidad == null) return null;
        return new ReservaResponse(
                entidad.getId(),
                entidad.getEstado(),
                entidad.getFecha_Entrada(),
                entidad.getFecha_Salida(),
                entidad.getNumHabitacion(),
                entidad.getIdHuesped(),
                entidad.getEstadoRegistro()
        );
    }
}
