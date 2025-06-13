package org.sci.serviciolibros.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
    info = @Info(
        title = "Servicio de Libros",
        version = "1.0",
        description = "API para gestionar préstamos de libros"
    )
)
@Configuration
public class OpenApiConfig {
}
