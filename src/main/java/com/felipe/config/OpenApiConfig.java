package com.felipe.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI().info(new Info().title("RESTful Api Conexa Challenger Java 21 and Spring Boot 3")
				.version("v1").description("Api RESTful for Conexa Challenger").termsOfService("")
				.license(new License().name("Apache 2.0").url("")));
	}
}
