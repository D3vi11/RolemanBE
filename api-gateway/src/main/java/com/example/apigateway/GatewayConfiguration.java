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
                .route("character", r -> r.path("/character")
                        .uri("http://localhost/8081"))
                .route("campaign", r -> r.path("/campaign")
                        .uri("http://localhost/8082"))
                .route("data", r -> r.path("/data")
                        .uri("http://localhost/8083"))
                .route("equipment", r -> r.path("/equipment")
                        .uri("http://localhost/8084"))
                .route("generator", r -> r.path("/generator")
                        .uri("http://localhost/8085"))
                .route("map", r -> r.path("/map")
                        .uri("http://localhost/8086"))
                .route("weather", r -> r.path("/weather")
                        .uri("http://localhost/8087"))
                .route("authorization", r -> r.path("/authorization")
                        .uri("http://localhost/8088"))
                .route("calendar", r -> r.path("/calendar")
                        .uri("http://localhost/8089"))
                .route("config-server", r -> r.path("/config-server")
                        .uri("http://localhost/8090"))
                .build();
    }
//@Bean
//public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//    return builder.routes()
//            .route("character", r -> r.path("/")
//                    .uri("https://google.com"))
//            .build();
//}
}
