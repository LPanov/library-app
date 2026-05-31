package com.library.app.apigateway.routes;

import org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.filter.FilterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.function.*;

import java.net.URI;

import static org.springframework.cloud.gateway.server.mvc.filter.FilterFunctions.setPath;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;

@Configuration
public class Routes {

    @Bean
    public RouterFunction<ServerResponse> genreServiceRoute() {
        return route("genre_service")
                .route(RequestPredicates.path("/api/v1/genres"), HandlerFunctions.http())
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("genreServiceCircuitBreaker",
                        URI.create("forward:/fallbackRoute")))
                .filter(FilterFunctions.uri(URI.create("http://localhost:8081")))
                .build();
    }

//    @Bean
//    public RouterFunction<ServerResponse> genreServiceSwaggerRoute() {
//        return route("genre_service_swagger")
//                .route(RequestPredicates.path("aggregate/genre-servicec/v3/api-docs"), HandlerFunctions.http())
//                .filter(FilterFunctions.uri(URI.create("http://localhost:8081")))
//                .filter(CircuitBreakerFilterFunctions.circuitBreaker("genreServiceSwaggerCircuitBreaker",
//                        URI.create("forward:/fallbackRoute")))
//                .filter(setPath("/api-docs"))
//                .build();
//    }

    @Bean
    public RouterFunction<ServerResponse> bookServiceRoute() {
        return route("book_service")
                .route(RequestPredicates.path("/api/v1/book"), HandlerFunctions.http())
                .filter(FilterFunctions.uri(URI.create("http://localhost:8082")))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("bookServiceCircuitBreaker",
                        URI.create("forward:/fallbackRoute")))
                .build();
    }


    @Bean
    public  RouterFunction<ServerResponse> fallbackRoute() {
        return route("fallbackRoute")
                .GET("/fallbackRoute", request -> ServerResponse
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body("Service Unavailable, please try again later"))
                .build();
    }
}
