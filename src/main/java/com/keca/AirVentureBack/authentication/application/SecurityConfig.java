package com.keca.AirVentureBack.authentication.application;

import com.keca.AirVentureBack.authentication.domain.service.JwtAuthenticationFilter;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

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
        http
                .authorizeHttpRequests((requests) -> requests
                        .anyRequest().permitAll()
                // .requestMatchers("/login", "/register").permitAll()
                // .requestMatchers("/user", "/user/**", "/password-change/**",
                // "avatar/**").authenticated()

                )
                /*
                 * .logout(logout ->
                 * logout
                 * .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                 * .logoutSuccessUrl("/login")
                 * .invalidateHttpSession(true)
                 * .deleteCookies("token")
                 * .permitAll()
                 * 
                 * )
                 */
                .csrf((csrf) -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) // withHttpOnlyTrue()
                        .ignoringRequestMatchers("/register", "/login")
                        .disable() // Décommentez pour désactiver en entier la protection CSRF en développement
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
