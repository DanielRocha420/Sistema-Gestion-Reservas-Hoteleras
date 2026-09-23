package com.reservashoteleras.reserva.service;

import com.daniel.commons.dto.reserva.ReservaRequest;
import com.daniel.commons.dto.reserva.ReservaResponse;
import com.daniel.commons.service.CrudService;

public interface ReservaService  extends CrudService<ReservaRequest, ReservaResponse> {
    boolean existsByHuespedId(Long id);
    boolean existsByHabitacionId(Long id);
    ReservaResponse checkIn(Long id, Long idHabitacion);
    ReservaResponse checkOut(Long id, Long idHabitacion);
    ReservaResponse cancelar(Long id, Long idHabitacion);
}
