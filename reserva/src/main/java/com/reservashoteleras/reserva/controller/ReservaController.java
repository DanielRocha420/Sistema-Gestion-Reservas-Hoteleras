package com.reservashoteleras.reserva.controller;

import com.daniel.commons.controller.CrudController;
import com.daniel.commons.dto.reserva.ReservaRequest;
import com.daniel.commons.dto.reserva.ReservaResponse;
import com.reservashoteleras.reserva.service.ReservaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservas")
@Tag(name = "API Reservas", description = "Métodos para la gestión de reservas")
public class ReservaController extends CrudController<ReservaRequest, ReservaResponse, ReservaService> {

    public ReservaController(ReservaService service) {
        super(service);
    }

    @PutMapping("/check-in/{id}")
    @Operation(summary = "Realizar check-in de una reserva")
    public ReservaResponse checkIn(
            @PathVariable Long id,
            @RequestParam Long numHabitacion
    ) {
        return service.checkIn(id, numHabitacion);
    }

    @PutMapping("/check-out/{id}")
    @Operation(summary = "Realizar check-out de una reserva")
    public ReservaResponse checkOut(
            @PathVariable Long id,
            @RequestParam Long numHabitacion
    ) {
        return service.checkOut(id, numHabitacion);
    }

    @PutMapping("/cancelar/{id}")
    @Operation(summary = "Cancelar una reserva")
    public ReservaResponse cancelar(
            @PathVariable Long id,
            @RequestParam Long numHabitacion
    ) {
        return service.cancelar(id, numHabitacion);
    }
}
