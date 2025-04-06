package com.hydroneo.ingestData.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
        .info(new Info()
        .title("Ingestion data to MongoDB")
        .version("1.0")
        .description("This is Ingestion data using Spring Boot and MongoDB")
        .contact(new Contact()
        .name("HydroNeo Dev Team")
        .email("dev@hydroneo.com")
        .url("https://www.hydroneo.com"))
        );

    }
}
