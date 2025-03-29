package com.api.urlshorter.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("URL Shorter")
                        .version("1.0")
                        .description("API para acortar URL")
                        .contact(new Contact()
                                .name("Samir López M.")
                                .email("lopezs.dev@gmail.com"))
                );
    }
}
