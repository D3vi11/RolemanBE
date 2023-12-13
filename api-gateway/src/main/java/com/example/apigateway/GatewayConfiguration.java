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
                .route("character", r -> r.order(1)
                        .path("/character/**")
                        .filters(f->f.rewritePath("/character","/"))
                        .uri("lb://character"))
                .route("campaign", r -> r.order(1)
                        .path("/campaign/**")
                        .filters(f->f.rewritePath("/campaign","/"))
                        .uri("lb://campaign"))
                .route("data", r -> r.order(1)
                        .path("/data/**")
                        .filters(f->f.rewritePath("/data","/"))
                        .uri("lb://data"))
                .route("equipment", r -> r.order(1)
                        .path("/equipment/**")
                        .filters(f->f.rewritePath("/equipment","/"))
                        .uri("lb://equipment"))
                .route("generator", r -> r.order(1)
                        .path("/generator/**")
                        .filters(f->f.rewritePath("/generator","/"))
                        .uri("lb://generator"))
                .route("map", r -> r.order(1)
                        .path("/map/**")
                        .filters(f->f.rewritePath("/map","/"))
                        .uri("lb://map"))
                .route("weather", r -> r.order(1)
                        .path("/weather/**")
                        .filters(f->f.rewritePath("/weather","/"))
                        .uri("lb://weather"))
                .route("authorization", r -> r.order(1)
                        .path("/authorization/**")
                        .filters(f->f.rewritePath("/authorization","/"))
                        .uri("lb://authorization"))
                .route("calendar", r -> r.order(1)
                        .path("/calendar/**")
                        .filters(f->f.rewritePath("/calendar","/"))
                        .uri("lb://calendar"))
                .build();
    }

    @Bean
    public RouteLocator swaggerRouteLocator(RouteLocatorBuilder builder){
        return builder.routes()
                .route(r->r.order(0)
                        .path("/authorization/*","/v3/api-docs/authorization")
                        .uri("lb://authorization"))
                .route(r->r.order(0)
                        .path("/calendar/*","/v3/api-docs/calendar")
                        .uri("lb://calendar"))
                .route(r->r.order(0)
                        .path("/character/*","/v3/api-docs/character")
                        .uri("lb://character"))
                .route(r->r.order(0)
                        .path("/campaign/*","/v3/api-docs/campaign")
                        .uri("lb://campaign"))
                .route(r->r.order(0)
                        .path("/data/*","/v3/api-docs/data")
                        .uri("lb://data"))
                .route(r->r.order(0)
                        .path("/equipment/*","/v3/api-docs/equipment")
                        .uri("lb://equipment"))
                .route(r->r.order(0)
                        .path("/generator/*","/v3/api-docs/generator")
                        .uri("lb://generator"))
                .route(r->r.order(0)
                        .path("/map/*","/v3/api-docs/map")
                        .uri("lb://map"))
                .route(r->r.order(0)
                        .path("/weather/*","/v3/api-docs/weather")
                        .uri("lb://weather"))
                .build();
    }
}
