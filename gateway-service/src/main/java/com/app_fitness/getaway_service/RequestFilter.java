package com.app_fitness.getaway_service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class RequestFilter implements GlobalFilter, Ordered {

    private final JwtService jwtService;

    /*
    Filtro per estratte il token, modificare Header della richiesta e inviare l'Id e i Ruoli dell' utente
     */

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain){
        log.info("[FILTRO GLOBALE] Filtro globale eseguito");
        ServerHttpRequest request = exchange.getRequest();

        String authHeader = request.getHeaders().getFirst("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            if (!jwtService.isTokenExpired(token) && jwtService.isValid(token)) {

                String userId = jwtService.estraiUserId(token);
                List<String> userRole = jwtService.estraiAuthorities(token);

                ServerHttpRequest.Builder changedRequestBuilder = request.mutate()
                        .header("X-User-Id", userId);

                userRole.forEach(role -> changedRequestBuilder.header("X-User-Role", role));
                ServerHttpRequest changedRequest = changedRequestBuilder.build();

                return chain.filter(exchange.mutate().request(changedRequest).build());
            }
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 1;
    }
}
