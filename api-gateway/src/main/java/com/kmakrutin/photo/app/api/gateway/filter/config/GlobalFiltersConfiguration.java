package com.kmakrutin.photo.app.api.gateway.filter.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Configuration
public class GlobalFiltersConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalFiltersConfiguration.class);

    @Order(1)
    @Bean
    public GlobalFilter prePostFilter() {
        return (ServerWebExchange exchange, GatewayFilterChain chain) -> {

            LOGGER.info("My global 1st pre-post-filter works before...");

            return chain.filter(exchange)
                    .then(Mono.fromRunnable(() -> LOGGER.info("My global 1st pre-post-filter works after...")));
        };
    }

    @Order(2)
    @Bean
    public GlobalFilter secondPrePostFilter() {
        return (ServerWebExchange exchange, GatewayFilterChain chain) -> {

            LOGGER.info("My global 2nd pre-post-filter works before...");

            return chain.filter(exchange)
                    .then(Mono.fromRunnable(() -> LOGGER.info("My global 2nd pre-post-filter works after...")));
        };
    }
}
