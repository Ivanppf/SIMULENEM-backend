package com.ifpbpj2.SIMULENEM_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "SimulENEM", version = "1", description = "API desenvolvida para elaborar provas"))
public class SimulenemBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimulenemBackendApplication.class, args);
	}

}
