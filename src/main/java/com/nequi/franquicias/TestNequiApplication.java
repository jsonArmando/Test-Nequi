package com.nequi.franquicias;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(
    info = @Info(
        title = "API de Franquicias",
        version = "1.0",
        description = "API para la gestión de franquicias, sucursales y productos"
    )
)
public class TestNequiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestNequiApplication.class, args);
	}

}
