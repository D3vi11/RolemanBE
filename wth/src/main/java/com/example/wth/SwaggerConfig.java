package com.example.wth;

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
                .info(new Info().title("Weather API")
                        .description("Weather API")
                        .version("1.0"));
    }
    @Bean
    public GroupedOpenApi publicApi(){
        return GroupedOpenApi.builder()
                .group("weather")
                .packagesToScan("com.example.wth.controller")
                .build();
    }
}
