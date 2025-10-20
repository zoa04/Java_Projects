package com.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource src = new UrlBasedCorsConfigurationSource();
        CorsConfiguration cfg = new CorsConfiguration();
        cfg.setAllowCredentials(true);
        cfg.setAllowedOrigins(List.of("http://localhost:3000")); // React dev server
        cfg.setAllowedHeaders(List.of("*") );
        cfg.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
        src.registerCorsConfiguration("/api/**", cfg);
        return new CorsFilter(src);
    }
}
