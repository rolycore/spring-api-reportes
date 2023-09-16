package com.sst.springapireportes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

    @Configuration
    public class CorsConfig {
        @Bean
        public WebMvcConfigurer WebMvcConfigurer(){
            return new WebMvcConfigurer(){
                @Override
                public void addCorsMappings(CorsRegistry registry) {
                    registry.addMapping("/authenticate")
                            .allowedOrigins("http://localhost:4200")
                            .exposedHeaders("*");
                    registry.addMapping("/api/**")
                            .allowedOrigins("http://localhost:4200")
                            .allowedMethods("*");
                }
            };
        }
    }

