package by.bsuir.apigateway.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.*;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;

import java.net.URI;
import java.util.List;
import java.util.function.Predicate;

@Component
public class AuthenticationGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthenticationGatewayFilterFactory.Config> {

    private final RestTemplate restTemplate;

    @Value("${urls.validate}")
    private String validateUrl;

    @Autowired
    public AuthenticationGatewayFilterFactory(RestTemplate restTemplate) {
        super(Config.class);
        this.restTemplate = restTemplate;
    }

    public static final List<String> openEndpoints = List.of(
            "/auth",
            "/eureka"
    );

    private final Predicate<ServerHttpRequest> isSecure =
            request -> openEndpoints.stream().noneMatch(uri -> request.getURI().getPath().contains(uri));

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (isSecure.test(exchange.getRequest())) {
                ResponseEntity<Long> responseEntity = sendValidationRequest(exchange);
                if (responseEntity.getStatusCode().is2xxSuccessful()) {
                    ServerHttpRequest request = addUserIdHeader(responseEntity.getBody(), exchange);
                    return chain.filter(
                            exchange.mutate()
                                    .request(request)
                                    .build()
                    );
                } else {
//                    throw new RuntimeException("Request failed with status code: " + responseEntity.getStatusCode());
                    System.out.println("Authentication Failed");
                    exchange.getResponse().setRawStatusCode(HttpStatus.UNAUTHORIZED.value());
                    return chain.filter(
                            exchange.mutate()
                                    .build()
                    );
                }
            }
            return chain.filter(
                    exchange.mutate()
                            .build()
            );
        };
    }

    private ResponseEntity<Long> sendValidationRequest(ServerWebExchange exchange) {
        HttpHeaders headers = new HttpHeaders();
        String jwt = exchange.getRequest().getHeaders().getFirst("Authorization");
        headers.set("Authorization", jwt);
        RequestEntity<Void> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, URI.create(validateUrl));
        try {
            return restTemplate.exchange(requestEntity, Long.class);
        } catch (RestClientException e) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(401));
        }
    }

    private ServerHttpRequest addUserIdHeader(Long userId, ServerWebExchange exchange) {
        return exchange.getRequest()
                .mutate()
                .header("userId", String.valueOf(userId))
                .build();
    }

    public static class Config {
        // ...
    }
}
