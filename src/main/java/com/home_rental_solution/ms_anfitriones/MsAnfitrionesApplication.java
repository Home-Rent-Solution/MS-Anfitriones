package com.home_rental_solution.ms_anfitriones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MsAnfitrionesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsAnfitrionesApplication.class, args);
	}

}
