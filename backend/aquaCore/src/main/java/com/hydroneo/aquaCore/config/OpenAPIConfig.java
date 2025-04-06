package com.hydroneo.aquaCore.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("User and Pond Information Services")
                        .version("1.0")
                        .description("This is User and Pond Information Services using Spring Boot and PosgreSQL")
                        .contact(new Contact()
                                .name("HydroNeo Dev Team")
                                .email("dev@hydroneo.com")
                                .url("https://www.hydroneo.com"))
                );

    }
}
