package com.taskmanager.config;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.*;
import org.springframework.context.annotation.*;


@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI taskManagerOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("Task Manager API")
                        .description("REST API для управления задачами и проектами")
                        .version("1.0.0")
                        .contact(new Contact().name("Michael").email("holevhuik@yandex.ru")
                        )
                );
    }
}
