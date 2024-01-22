package com.example.apigateway.filter;

import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AuthorizationFilter implements GatewayFilter, Ordered {

    private final WebClient.Builder webClient;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if(!exchange.getRequest().getHeaders().containsKey("Authorization")){
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        if(exchange.getRequest().getHeaders().get("Authorization").isEmpty()){
           exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
           return exchange.getResponse().setComplete();
        }
        return webClient
                .build()
                .get()
                .uri("http://authorization/validate")
                .header("Authorization", exchange.getRequest().getHeaders().get("Authorization").get(0))
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(value -> {
                    JSONObject jsonObject = new JSONObject(value);
                    Boolean isValid = (Boolean) jsonObject.get("valid");
                    if (!isValid) {
                        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                        return exchange.getResponse().setComplete();
                    }
                    return chain.filter(exchange);
                });
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
