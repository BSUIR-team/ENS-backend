package by.bsuir.apigateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class GatewayFilterFactory extends AbstractGatewayFilterFactory<GatewayFilterFactory.Config> {

    public static final List<String> openEndpoints = List.of(
            "/auth"
    );

    private final Predicate<ServerHttpRequest> isSecure =
            request -> openEndpoints.stream().noneMatch(uri -> request.getURI().getPath().contains(uri));

    @Override
    public GatewayFilter apply(Config config) {
//        return ((exchange, chain) -> {
//
//        });
        return null;
    }

    public static class Config {
        // ...
    }
}
