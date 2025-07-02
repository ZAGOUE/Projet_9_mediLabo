package com.assessment.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                // Injecte l'en-tête d’authentification Basic pour "admin:admin123"
                String credentials = "admin:admin123";
                String encoded = java.util.Base64.getEncoder().encodeToString(credentials.getBytes());
                template.header("Authorization", "Basic " + encoded);
            }
        };
    }
}
