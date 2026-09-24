package com.daniel.commons.client;

import com.daniel.commons.configuration.FeignClientConfig;
import com.daniel.commons.dto.huesped.HuespedResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "huespedes", configuration = FeignClientConfig.class)
public interface HuespedClient {

    @GetMapping("/{id}")
    HuespedResponse obtenerPorId(@PathVariable("id") Long id);
}
