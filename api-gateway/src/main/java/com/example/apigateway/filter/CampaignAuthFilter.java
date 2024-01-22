package com.example.apigateway.filter;

import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class CampaignAuthFilter implements GatewayFilter, Ordered {
    private final WebClient.Builder webClient;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        List<String> requiredParams = List.of("campaignId", "username");
        MultiValueMap<String, String> queryParams = exchange.getRequest().getQueryParams();
        if(queryParams.isEmpty()){
            exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
            return exchange.getResponse().setComplete();
        }
        for(String param:requiredParams){
            if(!queryParams.containsKey(param)){
                exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                return exchange.getResponse().setComplete();
            }
        }
        for(String param: requiredParams){
            if(queryParams.get(param).isEmpty()){
                exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                return exchange.getResponse().setComplete();
            }
        }
        return webClient
                .build()
                .get()
                .uri("http://campaign/verify?=campaignId=" + queryParams.get("campaignId").get(0) + "&username=" + queryParams.get("username").get(0))
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(value -> {
                    JSONObject jsonObject = new JSONObject(value);
                    String response = (String) jsonObject.get("message");
                    if (response.equals("DENY")) {
                        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
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
