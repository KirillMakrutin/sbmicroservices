package com.kmakrutin.photo.app.api.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class MyGlobalPreFilter implements GlobalFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyGlobalPreFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        LOGGER.info("My first prefilter works...");

        final ServerHttpRequest request = exchange.getRequest();

        LOGGER.info("Path {}", request.getPath().toString());

        request.getHeaders().forEach((name, value) -> LOGGER.info("Header {}: {}", name, value));

        return chain.filter(exchange);
    }
}
