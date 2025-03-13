package com.keca.AirVentureBack.authentication.application;


import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


import org.springframework.lang.NonNull;

@Configuration
public class ApiConfig implements WebMvcConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(ApiConfig.class);
    
    
    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry) {
        String allowedOrigins = System.getenv("CORS_ALLOWED_ORIGINS");

    if (allowedOrigins == null || allowedOrigins.isEmpty()) {
        logger.error("⚠️ CORS_ALLOWED_ORIGINS is not set or empty!");
    } else {
        logger.info("✅ Setting CORS Allowed Origins: {}", Arrays.asList(allowedOrigins.split(",")));
    }

    registry.addMapping("/**")
        .allowedOrigins(allowedOrigins != null ? allowedOrigins.split(",") : new String[]{})
        .allowedMethods("GET", "POST", "PUT", "DELETE")
        .allowedHeaders("*")
        .allowCredentials(true);
    
}
}

