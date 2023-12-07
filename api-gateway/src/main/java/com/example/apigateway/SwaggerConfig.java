package com.example.apigateway;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("Roleman API")
                        .description("Roleman API")
                        .version("1.0"));
    }

    @Bean
    public GroupedOpenApi authorizationApi(){
        return GroupedOpenApi.builder()
                .group("authorization")
                .pathsToMatch("/authorization/**")
                .build();
    }
    @Bean
    public GroupedOpenApi calendarApi(){
        return GroupedOpenApi.builder()
                .group("calendar")
                .pathsToMatch("/calendar/**")
                .build();
    }
    @Bean
    public GroupedOpenApi characterApi(){
        return GroupedOpenApi.builder()
                .group("character")
                .pathsToMatch("/character/**")
                .build();
    }
    @Bean
    public GroupedOpenApi dataApi(){
        return GroupedOpenApi.builder()
                .group("data")
                .pathsToMatch("/data/**")
                .build();
    }
    @Bean
    public GroupedOpenApi equipmentApi(){
        return GroupedOpenApi.builder()
                .group("equipment")
                .pathsToMatch("/equipment/**")
                .build();
    }
    @Bean
    public GroupedOpenApi generatorApi(){
        return GroupedOpenApi.builder()
                .group("generator")
                .pathsToMatch("/generator/**")
                .build();
    }
    @Bean
    public GroupedOpenApi mapApi(){
        return GroupedOpenApi.builder()
                .group("map")
                .pathsToMatch("/map/**")
                .build();
    }
    @Bean
    public GroupedOpenApi weatherApi(){
        return GroupedOpenApi.builder()
                .group("weather")
                .pathsToMatch("/weather/**")
                .build();
    }
}
