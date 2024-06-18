package com.senai.pa3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@SpringBootApplication
public class ProjetoAplicadoIiiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjetoAplicadoIiiApplication.class, args);
	}

}
