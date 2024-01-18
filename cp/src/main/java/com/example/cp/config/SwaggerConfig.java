package com.example.cp.config;

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
                .info(new Info().title("Campaign API")
                        .description("Campaign API")
                        .version("1.0"));
    }

    @Bean
    public GroupedOpenApi publicApi(){
        return GroupedOpenApi.builder()
                .group("campaign")
                .packagesToScan("com.example.cp.controller")
                .build();
    }
}
