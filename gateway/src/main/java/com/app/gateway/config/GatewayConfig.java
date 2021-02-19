package com.app.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.factory.TokenRelayGatewayFilterFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class GatewayConfig {

    private TokenRelayGatewayFilterFactory filterFactory;

    @Autowired
    public GatewayConfig(TokenRelayGatewayFilterFactory filterFactory) {
        this.filterFactory = filterFactory;
    }

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("threadpost", r -> r.path("/threadpost/**")
                        .filters(f -> f
                                .filters(filterFactory.apply())
                                .removeRequestHeader("Cookie"))
                        .uri("lb://threadpost"))
                .build();
    }


    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .csrf().disable()
                .authorizeExchange(auth -> auth
                        .anyExchange().permitAll()
                );
        return http.build();
    }

//    @Bean
//    ReactiveClientRegistrationRepository clientRegistrationRepository(){
//        return new InMemoryReactiveClientRegistrationRepository(clientRegistration());
//    }
//
//    @Bean
//    ClientRegistration clientRegistration(){
//        return CommonOAuth2Provider.GITHUB
//                .getBuilder("github")
//                .clientId("bdd840ccacd861b65927")
//                .clientSecret("3a101c1d6332eefafd2969c079f3308709961acc")
//                .build();
//    }

//    @Bean
//    ReactiveClientRegistrationRepository googleRegistrationRepository(){
//        return new InMemoryReactiveClientRegistrationRepository(clientRegistrationGoogle());
//    }
//
//    @Bean
//    ClientRegistration clientRegistrationGoogle(){
//        return CommonOAuth2Provider.GOOGLE
//                .getBuilder("google")
//                .clientId("684390368964-6nfsrd7fi33ff4ac9uu686f1k3ve3rsl.apps.googleusercontent.com")
//                .clientSecret("eqy04nf_4etvdLID-hHkM47c")
//                .build();
//    }

}
