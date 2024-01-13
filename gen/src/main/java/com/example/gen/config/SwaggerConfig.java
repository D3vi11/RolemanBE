package com.example.gen.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI calendarApi(){
        return new OpenAPI()
                .info(new Info().title("Generator API")
                        .description("Generator API")
                        .version("1.0"));
    }
    @Bean
    public GroupedOpenApi publicApi(){
        return GroupedOpenApi.builder()
                .group("generator")
                .packagesToScan("com.example.gen.controller")
                .build();
    }
}
