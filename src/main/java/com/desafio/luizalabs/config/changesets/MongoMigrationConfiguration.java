package com.desafio.luizalabs.config.changesets;

import com.github.cloudyrock.mongock.SpringBootMongock;
import com.github.cloudyrock.mongock.SpringBootMongockBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;

@Profile("!test")
@Configuration
public class MongoMigrationConfiguration {

    @Bean
    public SpringBootMongock mongock(
      ApplicationContext springContext,
      MongoTemplate mongoTemplate,
      @Value("${spring.data.mongodb.database}") String databaseName
    ) {
        return new SpringBootMongockBuilder(mongoTemplate, "com.desafio.luizalabs.config.changesets")
                .setApplicationContext(springContext)
                .setLockQuickConfig()
                .build();
    }

}
