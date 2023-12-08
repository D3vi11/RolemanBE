package com.example.apigateway;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.properties.SwaggerUiConfigParameters;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class SwaggerConfig {


    @Bean
    public OpenAPI apiConfig() {
        return new OpenAPI()
                .info(new Info()
                        .title("Roleman API")
                        .description("Roleman API")
                        .version("1.0"));
    }

    @Bean
    public CommandLineRunner openApiGroups(
            RouteDefinitionLocator locator,
            SwaggerUiConfigParameters swaggerUiParameters) {
        return args -> Objects.requireNonNull(locator
                        .getRouteDefinitions().collectList().block())
                .stream()
                .map(RouteDefinition::getId)
                .filter(id -> id.matches("ReactiveCompositeDiscoveryClient_.*"))
                .map(id -> id.replace("ReactiveCompositeDiscoveryClient_", "").toLowerCase())
                .filter(this::removeConfigs)
                .forEach(swaggerUiParameters::addGroup);
    }

    private boolean removeConfigs(String value){
        if(value.equals("config-server")||value.equals("api-gateway")){
            return false;
        }
        return true;
    }


}
