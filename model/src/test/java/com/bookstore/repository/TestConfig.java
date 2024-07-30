package com.bookstore.repository;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages = "com.bookstore")
@EnableJpaRepositories(basePackages = "com.bookstore.repository")
@EntityScan(basePackages = "com.bookstore.model")
public class TestConfig {
}