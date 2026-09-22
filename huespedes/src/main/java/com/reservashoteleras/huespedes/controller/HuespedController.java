package com.reservashoteleras.huespedes.controller;

import com.daniel.commons.controller.CrudController;
import com.daniel.commons.dto.huesped.HuespedRequest;
import com.daniel.commons.dto.huesped.HuespedResponse;
import com.reservashoteleras.huespedes.service.HuespedService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "API Huespedes", description = "Metodos para la gestion de los huespedes")
public class HuespedController extends CrudController<HuespedRequest, HuespedResponse, HuespedService> {

    public HuespedController(HuespedService service) {
        super(service);
    }

    @GetMapping("/id_huesped/{id}")
    @Operation(
            summary = "Obtener Huesped id por id"
    )
    public HuespedResponse obtenerHuesped(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id)).getBody();
    }


}
