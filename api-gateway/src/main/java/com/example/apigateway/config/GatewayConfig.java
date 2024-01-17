package com.example.apigateway.config;

import com.example.apigateway.filter.AuthorizationFilter;
import org.json.JSONObject;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder, AuthorizationFilter authorizationFilter) {
        return builder.routes()
                .route("character", r -> r.path("/character/**")
                        .filters(f->f.rewritePath("/character","/")
                                //.filter(authorizationFilter)
                                 )
                        .uri("lb://character"))
                .route("campaign", r -> r.path("/campaign/**")
                        .filters(f->f.rewritePath("/campaign","/")
                                //.filter(authorizationFilter)
                                )
                        .uri("lb://campaign"))
                .route("data", r -> r.path("/data/**")
                        .filters(f->f.rewritePath("/data","/")
                                //.filter(authorizationFilter)
                                )
                        .uri("lb://data"))
                .route("equipment", r -> r.path("/equipment/**")
                        .filters(f->f.rewritePath("/equipment","/")
                                //.filter(authorizationFilter)
                                )
                        .uri("lb://equipment"))
                .route("generator", r -> r.path("/generator/**")
                        .filters(f->f.rewritePath("/generator","/")
                                //.filter(authorizationFilter)
                                )
                        .uri("lb://generator"))
                .route("map", r -> r.path("/map/**")
                        .filters(f->f.rewritePath("/map","/")
                                //.filter(authorizationFilter)
                                )
                        .uri("lb://map"))
                .route("weather", r -> r.path("/weather/**")
                        .filters(f->f.rewritePath("/weather","/")
                                //.filter(authorizationFilter)
                                )
                        .uri("lb://weather"))
                .route("authorization", r -> r.path("/authorization/**")
                        .filters(f->f.rewritePath("/authorization","/"))
                        .uri("lb://authorization"))
                .route("calendar", r -> r.path("/calendar/**")
                        .filters(f->f.rewritePath("/calendar","/")
                               // .filter(authorizationFilter)
                                )
                        .uri("lb://calendar"))
                .build();
    }

    @Bean
    public RouteLocator swaggerRouteLocator(RouteLocatorBuilder builder){
        return builder.routes()
                .route(r->r.path("/v3/api-docs/authorization")
                        .uri("lb://authorization"))
                .route(r->r.path("/v3/api-docs/calendar")
                        .uri("lb://calendar"))
                .route(r->r.path("/v3/api-docs/character")
                        .uri("lb://character"))
                .route(r->r.path("/v3/api-docs/campaign")
                        .uri("lb://campaign"))
                .route(r->r.path("/v3/api-docs/data")
                        .uri("lb://data"))
                .route(r->r.path("/v3/api-docs/equipment")
                        .uri("lb://equipment"))
                .route(r->r.path("/v3/api-docs/generator")
                        .uri("lb://generator"))
                .route(r->r.path("/v3/api-docs/map")
                        .uri("lb://map"))
                .route(r->r.path("/v3/api-docs/weather")
                        .uri("lb://weather"))
                .build();
    }
}
