package com.app.thread.config;

import com.app.thread.model.Post;
import com.app.thread.model.ThreadPost;
import com.app.thread.repo.PostRepo;
import com.app.thread.repo.ThreadRepo;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.model.Indexes;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.bson.BsonDocument;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.IntStream;

@Configuration
@Log4j2
public class MongoConfig extends AbstractReactiveMongoConfiguration {


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
        client.getDatabase(getDatabaseName())
                .getCollection("region")
                .createIndex(Indexes.geo2dsphere("zipPoly"));
        return client;
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
