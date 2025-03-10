package com.keca.AirVentureBack;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class TestSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        .authorizeHttpRequests(authz -> authz
            .requestMatchers("/login", "/register", "/swagger-ui/**").permitAll()  // Autorise l'accès sans authentification
            .anyRequest().authenticated()  // Toutes les autres requêtes nécessitent une authentification
        )
        .csrf(csrf -> csrf.disable());  // Désactive CSRF pour les tests

    return http.build();
    }
}