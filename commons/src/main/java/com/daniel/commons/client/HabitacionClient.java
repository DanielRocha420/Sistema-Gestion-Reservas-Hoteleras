package com.daniel.commons.client;

import com.daniel.commons.dto.habitacion.HabitacionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "habitaciones", url = "${habitaciones.service.url}")
public interface HabitacionClient {

    @GetMapping("/habitaciones/{id}")
    HabitacionResponse obtenerPorId(@PathVariable("id") Long id);

    @PutMapping("/habitaciones/{id}/estado/{idEstado}")
    void actualizarEstado(@PathVariable("id") Long id, @PathVariable("idEstado") Long idEstado);

    @PutMapping("/habitaciones/{id}/liberar")
    void liberar(@PathVariable("id") Long id);
}
