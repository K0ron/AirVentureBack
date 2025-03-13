package com.keca.AirVentureBack.authentication.application;

import com.keca.AirVentureBack.authentication.domain.service.JwtAuthenticationFilter;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
public class SecurityConfig {
        private final JwtAuthenticationFilter jwtAuthenticationFilter;

        public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
                this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        }

        @Bean
        public BCryptPasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
               // String allowedOrigins = System.getenv("CORS_ALLOWED_ORIGINS");
                http
                // .cors(cors -> cors.configurationSource(request -> {
                //     CorsConfiguration config = new CorsConfiguration();
                //     config.setAllowedOrigins(List.of(allowedOrigins));
                //     config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
                //     config.setAllowedHeaders(List.of("*"));
                //     config.setAllowCredentials(true);
                //     return config;
                // }))
                .csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                                  .ignoringRequestMatchers("/register", "/login", "/logged-out", "/activities", "activity/**")
                                  .disable()) 
                .authorizeHttpRequests(requests -> requests
                    .requestMatchers("/login", "/register", "/logged-out", "/activities").permitAll()
                    .requestMatchers("/v3/api-docs", "/swagger-resources/**", "/swagger-ui/index.html", "/webjars/**").permitAll()
                    .anyRequest().authenticated())
                .logout(logout -> logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/logged-out")
                    .invalidateHttpSession(true)
                    .deleteCookies("token")
                    .permitAll())
                .sessionManagement(session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        
            http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        
            return http.build();
        }

}
