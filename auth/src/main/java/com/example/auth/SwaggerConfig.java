package com.example.auth;

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
                .info(new Info().title("Authorization API")
                        .description("Authorization API")
                        .version("1.0"));
    }

    @Bean
    public GroupedOpenApi publicApi(){
        return GroupedOpenApi.builder()
                .group("authorization")
                .packagesToScan("com.example.auth.controller")
                .build();
    }
}
