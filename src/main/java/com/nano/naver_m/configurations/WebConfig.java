package com.nano.naver_m.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer{
	private final String frontEndDomain = "https://naver-mock-app.herokuapp.com";
   @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/users/*/cart/**").allowedOrigins(frontEndDomain)
			.allowedMethods("DELETE", "POST", "GET");
        registry.addMapping("/register").allowedOrigins(frontEndDomain)
        	.allowedMethods("POST");
        registry.addMapping("/login").allowedOrigins(frontEndDomain)
    		.allowedMethods("POST");
        registry.addMapping("/api/users/**").allowedOrigins(frontEndDomain)
    		.allowedMethods("POST", "GET", "PUT", "DELETE");
        registry.addMapping("/users/*").allowedOrigins(frontEndDomain)
    		.allowedMethods("GET");
        registry.addMapping("/api/products/**").allowedOrigins(frontEndDomain)
    		.allowedMethods("GET");
        
    }
}
