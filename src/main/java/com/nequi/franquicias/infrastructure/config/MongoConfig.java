package com.nequi.franquicias.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.nequi.franquicias.infrastructure.adapters.output.persistence.repository")
public class MongoConfig {
}