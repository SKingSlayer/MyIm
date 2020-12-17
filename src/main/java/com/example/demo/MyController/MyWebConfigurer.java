package com.example.demo.MyController;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootConfiguration
public class MyWebConfigurer implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/cors/**").
                allowedHeaders("*").
                allowedMethods("*").
                maxAge(1800).
                allowedOrigins("*");

        registry.addMapping("/**").
                allowedHeaders("*").
                allowedMethods("*").
                maxAge(1800).
                allowedOrigins("http://localhost:8081");
    }

}