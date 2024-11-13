package com.keca.AirVentureBack;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("AirVenture")
                .pathsToMatch("/**")
                .build();
    }

    private OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("AirVentureAPI")
                        .version("1.0")
                        .description("API app AirVenture")
                        .contact(new Contact()
                                .name("Kévin CARON")
                                .email("keivn.caron.91@gmail.com")));

    }

}
