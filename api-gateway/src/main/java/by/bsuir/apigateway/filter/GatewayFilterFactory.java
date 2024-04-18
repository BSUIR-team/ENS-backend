package by.bsuir.apigateway.filter;

import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

@Component
public class GatewayFilter extends AbstractGatewayFilterFactory<GatewayFilter.Config> {

    public static class Config {
        // ...
    }
}
