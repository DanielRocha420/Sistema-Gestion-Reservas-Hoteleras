package com.reservashoteleras.reserva.service;

import com.daniel.commons.dto.reserva.ReservaRequest;
import com.daniel.commons.dto.reserva.ReservaResponse;
import com.daniel.commons.service.CrudService;

public interface ReservaService  extends CrudService<ReservaRequest, ReservaResponse> {
    ReservaResponse checkIn(Long id, Long numHabitacion);
    ReservaResponse checkOut(Long id, Long numHabitacion);
    ReservaResponse cancelar(Long id, Long numHabitacion);
}
