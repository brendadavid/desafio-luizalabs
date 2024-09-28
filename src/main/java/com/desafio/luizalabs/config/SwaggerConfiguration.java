package com.desafio.luizalabs.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public GroupedOpenApi wishListApi(@Value("${VERSION:latest}") String version) {
        return GroupedOpenApi.builder()
                .group("WishList")
                .packagesToScan("com.desafio.luizalabs.wishlist")
                .build();
    }
}
