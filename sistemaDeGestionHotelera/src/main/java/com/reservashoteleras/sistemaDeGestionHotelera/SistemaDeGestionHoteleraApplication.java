package com.reservashoteleras.sistemaDeGestionHotelera;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class SistemaDeGestionHoteleraApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemaDeGestionHoteleraApplication.class, args);
	}

}
