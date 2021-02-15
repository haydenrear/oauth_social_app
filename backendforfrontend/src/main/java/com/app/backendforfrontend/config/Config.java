package com.app.backendforfrontend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Configuration
@EnableWebFluxSecurity
public class Config {

    @Value("${google.auth.cert.enpoint}")
    String cert;

    @Bean
    ReactiveClientRegistrationRepository googleRegistrationRepository(){
        return new InMemoryReactiveClientRegistrationRepository(clientRegistrationGoogle());
    }

    @Bean
    ClientRegistration clientRegistrationGoogle(){
        return CommonOAuth2Provider.GOOGLE
                .getBuilder("google")
                .clientId("684390368964-6nfsrd7fi33ff4ac9uu686f1k3ve3rsl.apps.googleusercontent.com")
                .clientSecret("eqy04nf_4etvdLID-hHkM47c")
                .build();
    }

    @Bean
    SecurityWebFilterChain filterChain(ServerHttpSecurity http){
        http.oauth2Client().and()
                .authorizeExchange(authorize -> authorize
                        .pathMatchers(HttpMethod.GET, "/threadPost/**").authenticated()
                        .pathMatchers(HttpMethod.GET, "/login/**").authenticated()
                        .pathMatchers(HttpMethod.GET,"/**").permitAll()
                ).oauth2Login()
                .clientRegistrationRepository(googleRegistrationRepository())
                .and().oauth2ResourceServer().jwt();
        return http.build();
    }

    @Bean
    ReactiveJwtDecoder decoder(){
        return NimbusReactiveJwtDecoder.withJwkSetUri(cert).build();
    }

    @Bean
    public WebClient.Builder builder() {
        return WebClient.builder();
    }

    @Bean
    public ReactiveOAuth2AuthorizedClientManager authorizedClientManager(
            ReactiveClientRegistrationRepository clientRegistrationRepository,
            ReactiveOAuth2AuthorizedClientService authorizedClientService) {

        ReactiveOAuth2AuthorizedClientProvider authorizedClientProvider =
                ReactiveOAuth2AuthorizedClientProviderBuilder.builder()
                        .clientCredentials()
                        .build();

        AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager authorizedClientManager =
                new AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager(
                        clientRegistrationRepository, authorizedClientService);
        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);

        return authorizedClientManager;
    }

    @Bean
    public WebClient webClient(ReactiveOAuth2AuthorizedClientManager authorizedClientManager, WebClient.Builder builder) {
        ServerOAuth2AuthorizedClientExchangeFilterFunction oauth =new ServerOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager);
        return builder.filter(oauth).build();
    }

}
