package com.reservashoteleras.reserva.controller;

import com.daniel.commons.dto.reserva.ReservaResponse;
import com.reservashoteleras.reserva.service.ReservaService;
import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @PutMapping("/{id}/check-in")
    public ReservaResponse checkIn(
            @PathVariable @Positive(message = "El ID debe ser positivo") Long id,
            @RequestParam @Positive(message = "El ID de la habitacion debe ser positivo") Long idHabitacion
    ) {
        return reservaService.checkIn(id, idHabitacion);
    }

    @PutMapping("/{id}/check-out")
    public ReservaResponse checkOut(
            @PathVariable @Positive(message = "El ID debe ser positivo") Long id,
            @RequestParam @Positive(message = "El ID de la habitacion debe ser positivo") Long idHabitacion
    ) {
        return reservaService.checkOut(id, idHabitacion);
    }

    @PutMapping("/{id}/cancelar")
    public ReservaResponse cancelar(
            @PathVariable @Positive(message = "El ID debe ser positivo") Long id,
            @RequestParam @Positive(message = "El ID de la habitacion debe ser positivo") Long idHabitacion
    ) {
        return reservaService.cancelar(id, idHabitacion);
    }
}
