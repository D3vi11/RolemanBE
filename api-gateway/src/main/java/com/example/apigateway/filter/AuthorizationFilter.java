package com.example.apigateway.filter;

import com.example.apigateway.exception.IncorrectTokenException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthorizationFilter implements GatewayFilter, Ordered {

    @Value("${auth.url}")
    private String authUrl;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        WebClient webClient = WebClient.create(authUrl);
        String response = webClient
                .get()
                .uri("/validate")
                .retrieve()
                .bodyToMono(String.class)
                .block();
        JSONObject jsonObject = new JSONObject(response);
        Boolean isValid = (Boolean)jsonObject.get("valid");
        if(!isValid){
            throw new IncorrectTokenException("Token jest niepoprawny");
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
