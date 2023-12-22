package com.example.apigateway.filter;

import com.example.apigateway.exception.IncorrectTokenException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
public class AuthorizationFilter implements GatewayFilter, Ordered {

    @Value("${auth.url}")
    private String authUrl;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        WebClient webClient = WebClient.create(authUrl);
        if(!exchange.getRequest().getHeaders().containsKey("Authorization")){
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        if(exchange.getRequest().getHeaders().get("Authorization").isEmpty()){
           exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
           return exchange.getResponse().setComplete();
        }
        return webClient
                .get()
                .uri("/validate")
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
