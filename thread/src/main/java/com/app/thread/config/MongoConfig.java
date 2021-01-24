package com.app.thread.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;

@Configuration
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
        return MongoClients.create(settings);
    }
}
