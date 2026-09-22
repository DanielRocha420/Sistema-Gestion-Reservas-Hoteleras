package com.reservashoteleras.huespedes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "com.reservashoteleras.huespedes",
        "com.daniel.commons.exceptions"
})
public class HuespedesApplication {

	public static void main(String[] args) {
		SpringApplication.run(HuespedesApplication.class, args);
	}

}
