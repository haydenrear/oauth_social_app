package com.app.backendforfrontend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrations;
import org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Properties;

@Configuration
@EnableWebFluxSecurity
public class Config {

  @Value("${google.auth.cert.enpoint}")
  String cert;
  @Value("${app.javamail.host}")
  private String mailHost;
  @Value("${app.javamail.port}")
  private int mailPort;
  @Value("${app.javamail.username}")
  private String mailUsername;
  @Value("${app.javamail.password}")
  private String mailPassword;
  @Value("${spring.security.oauth2.client.provider.keycloak.issuer-uri}")
  String keycloakIssuerUri;
  @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
  String keycloakJwt;
  @Value("${spring.security.oauth2.client.provider.keycloak.token-uri}")
  String tokenUri;

  @Bean
  ReactiveClientRegistrationRepository registrationRepository() {
    return new InMemoryReactiveClientRegistrationRepository(clientRegistrationKeycloak(), clientRegistrationGoogle());
  }

  @Bean
  ClientRegistration clientRegistrationGoogle() {
    return CommonOAuth2Provider.GOOGLE
      .getBuilder("google")
      .clientId("684390368964-6nfsrd7fi33ff4ac9uu686f1k3ve3rsl.apps.googleusercontent.com")
      .clientSecret("eqy04nf_4etvdLID-hHkM47c")
      .build();
  }

  @Bean
  ClientRegistration clientRegistrationKeycloak() {
    return ClientRegistration.withRegistrationId("keycloak")
      .clientId("backendforfrontend")
      .clientSecret("a39e0e41-1fd1-4af4-93e2-cf3f682527e5")
      .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
      .jwkSetUri(keycloakJwt)
      .issuerUri(keycloakIssuerUri)
      .tokenUri(tokenUri)
      .build();
  }

  @Bean
  SecurityWebFilterChain filterChain(ServerHttpSecurity http) {
    http.oauth2Client().and().csrf().disable()
      .authorizeExchange(authorize -> authorize
        .pathMatchers(HttpMethod.GET, "/threadPost/**").authenticated()
        .pathMatchers(HttpMethod.GET, "/login/**").authenticated()
        .pathMatchers(HttpMethod.GET, "/filterBy/**").permitAll()
        .pathMatchers(HttpMethod.GET, "/**").permitAll()
        .pathMatchers(HttpMethod.POST, "/newProperty/**").permitAll()
        .pathMatchers(HttpMethod.POST, "/getPhotos/**").permitAll()
        .pathMatchers(HttpMethod.POST, "/uploadPhoto/**").permitAll()
        .pathMatchers(HttpMethod.POST, "/newPost/**").permitAll()
      ).oauth2Login()
      .clientRegistrationRepository(registrationRepository())
      .and().oauth2ResourceServer().jwt();
    return http.build();
  }

  @Bean
  ReactiveJwtDecoder decoder() {
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
    ServerOAuth2AuthorizedClientExchangeFilterFunction oauth = new ServerOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager);
    return builder.filter(oauth)
      .codecs(configurer -> {
        configurer.defaultCodecs().maxInMemorySize(1024 * 1024 * 50);
      }).build();
  }

  @Bean
  public JavaMailSender javaMail() {
    JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
    javaMailSender.setHost(mailHost);
    javaMailSender.setPort(mailPort);
    javaMailSender.setUsername(mailUsername);
    javaMailSender.setPassword(mailPassword);

    Properties properties = javaMailSender.getJavaMailProperties();
    properties.put("mail.transport.protocol", "smtp");
    properties.put("mail.smtp.auth", "true");
    properties.put("mail.smtp.starttls.enable", "true");
    properties.put("mail.debug", "true");
    return javaMailSender;
  }

}
