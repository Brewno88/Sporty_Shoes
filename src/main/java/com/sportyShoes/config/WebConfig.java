package com.sportyShoes.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**") // Allow CORS on all endpoints
        .allowedOriginPatterns("*") // Change to your allowed
        // origin
        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Specify allowed methods
        .allowedHeaders("*") // Allow all headers
        .allowCredentials(true); // Allow credentials
  }
}
