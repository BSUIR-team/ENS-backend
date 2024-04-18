package by.bsuir.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("api-server", r -> r.path("/users/**")
                        .uri("lb://api-server"))
                .route("authentication-server", r -> r.path("/auth/**")
                        .uri("lb://authentication-server"))
                .build();
    }
}
