package com.reservashoteleras.habitaciones.controller;

import com.daniel.commons.controller.CrudController;
import com.daniel.commons.dto.habitacion.HabitacionRequest;
import com.daniel.commons.dto.habitacion.HabitacionResponse;
import com.reservashoteleras.habitaciones.service.HabitacionService;
import jakarta.validation.constraints.Positive;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/habitaciones")
public class HabitacionController extends CrudController<HabitacionRequest, HabitacionResponse, HabitacionService> {

    public HabitacionController(HabitacionService service) {
        super(service);
    }

    @PutMapping("/{id}/tipo/{idTipo}")
    public void actualizarTipoHabitacion(
            @PathVariable @Positive(message = "El ID debe ser positivo") Long id,
            @PathVariable @Positive(message = "El ID de tipo debe ser positivo") Long idTipo
    ) {
        service.actualizarTipoHabitacion(id, idTipo);
    }

    @PutMapping("/{id}/estado/{idEstado}")
    public void actualizarEstado(
            @PathVariable @Positive(message = "El ID debe ser positivo") Long id,
            @PathVariable @Positive(message = "El ID de estado debe ser positivo") Long idEstado
    ) {
        service.actualizarEstado(id, idEstado);
    }

    @PutMapping("/{id}/liberar")
    public void liberar(
            @PathVariable @Positive(message = "El ID debe ser positivo") Long id
    ) {
        service.liberar(id);
    }
}
