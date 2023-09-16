package com.sst.springapireportes.config;

public class CorsConfig {
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
}
