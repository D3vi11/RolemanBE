package com.example.apigateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfiguration {
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("character", r -> r.path("/character/**")
                        .uri("lb://character"))
                .route("campaign", r -> r.path("/campaign/**")
                        .uri("lb://campaign"))
                .route("data", r -> r.path("/data/**")
                        .uri("lb://data"))
                .route("equipment", r -> r.path("/equipment/**")
                        .uri("lb://equipment"))
                .route("generator", r -> r.path("/generator/**")
                        .uri("lb://generator"))
                .route("map", r -> r.path("/map/**")
                        .uri("lb://map"))
                .route("weather", r -> r.path("/weather/**")
                        .uri("lb://weather"))
                .route("authorization", r -> r.path("/authorization/**")
                        .uri("lb://authorization"))
                .route("calendar", r -> r.path("/calendar/**")
                        .uri("lb://calendar"))
                .build();
    }
}
