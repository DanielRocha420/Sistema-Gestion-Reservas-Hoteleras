package com.reservashoteleras.reserva.client;

import com.daniel.commons.dto.huesped.HuespedResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "huespedes", url = "${huespedes.service.url}")
public interface HuespedClient {

    @GetMapping("/{id}")
    HuespedResponse obtenerPorId(@PathVariable("id") Long id);
}
