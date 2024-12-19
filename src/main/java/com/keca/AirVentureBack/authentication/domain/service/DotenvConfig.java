package com.keca.AirVentureBack.authentication.domain.service;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DotenvConfig {

    @Bean
    public Dotenv dotenv() {
        Dotenv dotenv = Dotenv.load();
        dotenv.entries().forEach(entry -> {
            System.setProperty(entry.getKey(), entry.getValue());
            System.out.println("Loaded env variable: " + entry.getKey() + " = " + entry.getValue());
        });
        return dotenv;
    }
}
