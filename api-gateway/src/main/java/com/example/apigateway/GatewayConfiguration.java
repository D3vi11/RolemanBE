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
                        .uri("lb://character"))
                .route("campaign", r -> r.order(1)
                        .path("/campaign/**")
                        .uri("lb://campaign"))
                .route("data", r -> r.order(1)
                        .path("/data/**")
                        .uri("lb://data"))
                .route("equipment", r -> r.order(1)
                        .path("/equipment/**")
                        .uri("lb://equipment"))
                .route("generator", r -> r.order(1)
                        .path("/generator/**")
                        .uri("lb://generator"))
                .route("map", r -> r.order(1)
                        .path("/map/**")
                        .uri("lb://map"))
                .route("weather", r -> r.order(1)
                        .path("/weather/**")
                        .uri("lb://weather"))
                .route("authorization", r -> r.order(1)
                        .path("/authorization/**")
                        .uri("lb://authorization"))
                .route("calendar", r -> r.order(1)
                        .path("/calendar/**")
                        .uri("lb://calendar"))
                .build();
    }

    @Bean
    public RouteLocator swaggerRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.order(0)
                        .path("/calendar/v3/api-docs")
                        .filters(f -> f.rewritePath("/calendar/v3/api-docs", "/v3/api-docs"))
                        .uri("lb://calendar"))
                .build();
    }

//    @Bean
//    public RouteLocator swaggerRouteLocator(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route(r -> r.order(0)
//                        .path("/authorization/swagger-ui/**")
//                        .filters(f -> f.rewritePath("/authorization/swagger-ui(?<segment>.*)", "/swagger-ui${segment}")
//                                .rewritePath("/authorization/v3/api-docs", "/v3/api-docs"))
//                        .uri("lb://authorization"))
//                .route(r -> r.order(0)
//                        .path("/calendar/swagger-ui/**")
//                        .filters(f -> f.rewritePath("/calendar/swagger-ui(?<segment>.*)", "/swagger-ui${segment}")
//                                .rewritePath("/calendar/v3/api-docs", "/v3/api-docs"))
//                        .uri("lb://calendar"))
//                .route(r -> r.order(0)
//                        .path("/character/swagger-ui/**")
//                        .filters(f -> f.rewritePath("/character/swagger-ui(?<segment>.*)", "/swagger-ui${segment}")
//                                .rewritePath("/character/v3/api-docs", "/v3/api-docs"))
//                        .uri("lb://character"))
//                .route(r -> r.order(0)
//                        .path("/campaign/swagger-ui/**")
//                        .filters(f -> f.rewritePath("/campaign/swagger-ui(?<segment>.*)", "/swagger-ui${segment}")
//                                .rewritePath("/campaign/v3/api-docs", "/v3/api-docs"))
//                        .uri("lb://campaign"))
//                .route(r -> r.order(0)
//                        .path("/data/swagger-ui/**")
//                        .filters(f -> f.rewritePath("/data/swagger-ui(?<segment>.*)", "/swagger-ui${segment}")
//                                .rewritePath("/data/v3/api-docs", "/v3/api-docs"))
//                        .uri("lb://data"))
//                .route(r -> r.order(0)
//                        .path("/equipment/swagger-ui/**")
//                        .filters(f -> f.rewritePath("/equipment/swagger-ui(?<segment>.*)", "/swagger-ui${segment}")
//                                .rewritePath("/equipment/v3/api-docs", "/v3/api-docs"))
//                        .uri("lb://equipment"))
//                .route(r -> r.order(0)
//                        .path("/generator/swagger-ui/**")
//                        .filters(f -> f.rewritePath("/generator/swagger-ui(?<segment>.*)", "/swagger-ui${segment}")
//                                .rewritePath("/generator/v3/api-docs", "/v3/api-docs"))
//                        .uri("lb://generator"))
//                .route(r -> r.order(0)
//                        .path("/map/swagger-ui/**")
//                        .filters(f -> f.rewritePath("/map/swagger-ui(?<segment>.*)", "/swagger-ui${segment}")
//                                .rewritePath("/map/v3/api-docs", "/v3/api-docs"))
//                        .uri("lb://map"))
//                .route(r -> r.order(0)
//                        .path("/weather/swagger-ui/**")
//                        .filters(f -> f.rewritePath("/weather/swagger-ui(?<segment>.*)", "/swagger-ui${segment}")
//                                .rewritePath("/weather/v3/api-docs", "/v3/api-docs"))
//                        .uri("lb://weather"))
//                .build();
//    }
}
