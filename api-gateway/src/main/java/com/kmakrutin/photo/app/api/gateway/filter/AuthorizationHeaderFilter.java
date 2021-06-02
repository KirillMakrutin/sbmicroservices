package com.kmakrutin.photo.app.api.gateway.filter;

import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationHeaderFilter.class);

    @Autowired
    private Environment environment;

    public AuthorizationHeaderFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {

            final ServerHttpRequest request = exchange.getRequest();

            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, "Not authorization header found", HttpStatus.UNAUTHORIZED);
            }

            final String bearerToken = Optional.ofNullable(request.getHeaders().get(HttpHeaders.AUTHORIZATION)).map(List::listIterator).map(ListIterator::next).orElseThrow();
            String jwt = bearerToken.replace("Bearer", "");

            if (!isJwtValid(jwt)) {
                return onError(exchange, "Jwt is not valid", HttpStatus.UNAUTHORIZED);
            }

            return chain.filter(exchange);
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, String errorMessage, HttpStatus status) {
        final ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);

        return response.setComplete();
    }

    private boolean isJwtValid(String jwt) {

        try {
            final String subject = Jwts.parser().setSigningKey(environment.getProperty("token.secret")).parseClaimsJws(jwt).getBody().getSubject();

            return subject != null && subject.length() > 0;
        } catch (Exception e) {
            LOGGER.error("Failed to validate JWT", e);
            return false;
        }
    }

    public static class Config {

    }
}
