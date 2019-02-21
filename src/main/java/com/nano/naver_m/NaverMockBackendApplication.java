package com.nano.naver_m;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class NaverMockBackendApplication {
	
	private final String frontEndDomain = "https://naver-mock-app.herokuapp.com/";
	
	public static void main(String[] args) {
		SpringApplication.run(NaverMockBackendApplication.class, args);
		
	}
	@Autowired(required = true)
	public void configureJackson(ObjectMapper jackson2ObjectMapper) {
	    jackson2ObjectMapper.setVisibility(PropertyAccessor.ALL, Visibility.ANY);
	}
	 @Bean
	    public WebMvcConfigurer corsConfigurer() {
	        return new WebMvcConfigurer() {
	            @Override
	            public void addCorsMappings(CorsRegistry registry) {
	            	registry.addMapping("/**").allowedOrigins(frontEndDomain);
//	                registry.addMapping("/users/*/cart/**").allowedOrigins(frontEndDomain)
//	            		.allowedMethods("DELETE", "POST", "GET");
//	                registry.addMapping("/register").allowedOrigins(frontEndDomain)
//	                	.allowedMethods("POST");
//	                registry.addMapping("/login").allowedOrigins(frontEndDomain)
//	            		.allowedMethods("POST");
//	                registry.addMapping("/api/users/**").allowedOrigins(frontEndDomain)
//	            		.allowedMethods("POST", "GET", "PUT", "DELETE");
//	                registry.addMapping("/users/*").allowedOrigins(frontEndDomain)
//	            		.allowedMethods("GET");
//	                registry.addMapping("/api/products/**").allowedOrigins(frontEndDomain)
//	            		.allowedMethods("GET");
	            }
	        };
	}
}

