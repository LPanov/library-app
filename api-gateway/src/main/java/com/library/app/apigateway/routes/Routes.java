package com.library.app.apigateway.routes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;

import java.net.URI;

@Configuration
public class Routes {

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("genre_service", r -> r.path("/api/v1/genres", "/api/v1/genres/**")
                        .filters(f -> f.circuitBreaker(config -> config
                                .setName("genreServiceCircuitBreaker")
                                .setFallbackUri("forward:/fallbackRoute")))
                        .uri("http://genre-service-app:8081"))

                .route("book_service", r -> r.path("/api/v1/book\", \"/api/v1/book/**")
                        .filters(f -> f.circuitBreaker(config -> config
                                .setName("bookServiceCircuitBreaker")
                                .setFallbackUri("forward:/fallbackRoute")))
                        .uri("http://book-service-app:8082"))

                .build();
    }
}