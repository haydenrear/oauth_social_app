package com.app.thread.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.model.Indexes;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.mongodb.reactivestreams.client.MongoCollection;
import lombok.extern.log4j.Log4j2;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@Log4j2
@EnableWebFluxSecurity
public class MongoConfig extends AbstractReactiveMongoConfiguration {

    @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
    String cert;

    @Override
    protected String getDatabaseName() {
        return "app";
    }

    @Override
    public MongoClient reactiveMongoClient() {
        ConnectionString connString = new ConnectionString(
                "mongodb+srv://***REMOVED***:***REMOVED***@alpaca.olrez.mongodb.net/app?retryWrites=true&w=majority"
        );
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connString)
                .retryWrites(true)
                .build();
        MongoClient client = MongoClients.create(settings);
        MongoCollection<Document> region = client.getDatabase(getDatabaseName())
                .getCollection("region");
        region.createIndex(Indexes.geo2dsphere("zipPoly"));
        region.createIndex(Indexes.geo2dsphere("location"));
        return client;
    }


    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .csrf()
                .disable()
                .authorizeExchange(auth -> auth
                        .anyExchange().authenticated()
                ).oauth2ResourceServer()
                .jwt().jwtDecoder(decoder());
        return http.build();
    }

    @Bean
    ReactiveJwtDecoder decoder(){
        return NimbusReactiveJwtDecoder.withJwkSetUri(cert).build();
    }

//    @Bean
//    ReactiveMongoTemplate reactiveMongoTemplate(MongoClient client){
//        return new ReactiveMongoTemplate(client, "app");
//    }

    @Bean
    public WebClient webClient(WebClient.Builder builder){
        return builder.build();
    }

}
