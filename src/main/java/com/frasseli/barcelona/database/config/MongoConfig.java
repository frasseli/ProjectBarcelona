package com.frasseli.barcelona.database.config;

import com.frasseli.barcelona.database.event.CascadingMongoEventListener;
import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories
public class MongoConfig extends AbstractMongoConfiguration {

    @Override
    protected String getDatabaseName() {
        return "barcelona";
    }

    @Override
    public MongoClient mongoClient() {
        return new MongoClient("127.0.0.1", 27017);
    }

    @Bean
    public CascadingMongoEventListener cascadingMongoEventListener() {
        return new CascadingMongoEventListener();
    }

}
