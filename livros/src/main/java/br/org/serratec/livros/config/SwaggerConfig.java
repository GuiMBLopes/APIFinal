package br.org.serratec.livros.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI config() {
		return new OpenAPI().info(new Info().title("Projeto individual").description("API do projeto individual Serratec 2024.2"));
	}
	
}
