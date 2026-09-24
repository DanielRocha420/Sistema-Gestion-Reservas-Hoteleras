package com.reservashoteleras.habitaciones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"com.reservashoteleras.habitaciones", "com.daniel.commons.dto", "com.daniel.commons.enums", "com.daniel.commons.exceptions", "com.daniel.commons.service", "com.daniel.commons.controller", "com.daniel.commons.mapper", "com.daniel.commons.utils"})
@EnableFeignClients(basePackages = {})
public class HabitacionesApplication {

	public static void main(String[] args) {
		SpringApplication.run(HabitacionesApplication.class, args);
	}

}
