package com.vod.config;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.vod.util.EnvProp;

@Configuration
@EnableMongoRepositories(basePackages = "com.vod")
public class MongoConfig extends AbstractMongoClientConfiguration {

	@Autowired
	private EnvProp env;
	
	 @Override
	  public boolean autoIndexCreation() {
	    return true;
	  }
	
	
	@Bean
    MongoTransactionManager transactionManager(MongoDbFactory  dbFactory) {
        return new MongoTransactionManager(dbFactory);
    }
    
 
    @Override
    protected String getDatabaseName() {
        return env.getMongodb();
    }
 
    @Override
    public MongoClient mongoClient() {
        final ConnectionString connectionString = new ConnectionString(env.getMongourl());
        final MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
            .applyConnectionString(connectionString)
            .build();
        return MongoClients.create(mongoClientSettings);
    }
    
    @Override
    public Collection<String> getMappingBasePackages() {
        return Collections.singleton("com.vod");
    }
    
    
    
}
