package com.reservashoteleras.habitaciones.service;

import com.daniel.commons.dto.habitacion.HabitacionRequest;
import com.daniel.commons.dto.habitacion.HabitacionResponse;
import com.daniel.commons.service.CrudService;

public interface HabitacionService extends CrudService<HabitacionRequest, HabitacionResponse> {

    void actualizarTipoHabitacion(Long numHabitacion, Long idTipo);

    void actualizarEstado(Long id, Long idEstado);

    void liberar(Long id);

    HabitacionResponse obtenerPorNumeroHabitacion(Long numHabitacion);
}
