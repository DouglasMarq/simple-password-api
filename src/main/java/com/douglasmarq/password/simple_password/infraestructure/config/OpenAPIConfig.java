package com.douglasmarq.password.simple_password.infraestructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Password API")
                                .description("Password API documentation")
                                .version("1.0")
                                .contact(
                                        new Contact()
                                                .name("Douglas Marques Alves")
                                                .email("douglas.marq.alves@outlook.com")
                                                .url("https://douglasmarq.github.io")));
    }
}
