package org.aelion.products.product.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration {
    @Bean // Bean instanciate automatically at the start of the application
    public WebMvcConfigurer corsConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry){
                registry
                        .addMapping("/**")
                        .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE")
                        // .allowedOrigins("http://localhost:4200", "http://127.0.0.1:4200")
                        .allowedOrigins("**") // This allows all origin - Not to do in normal scenario
                        .allowCredentials(true)
                        .maxAge(3600);
            };
        };
    }
}
