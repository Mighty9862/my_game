package org.example.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API для игры 'Своя Игра'")
                        .version("1.0")
                        .description("Документация API для бэкенда игры 'Своя Игра'. Здесь описаны все эндпоинты для создания игр, команд, категорий, вопросов, а также управления процессом игры (запуск, выбор вопросов, присвоение очков и т.д.).")
                        .termsOfService("http://example.com/terms/")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
}