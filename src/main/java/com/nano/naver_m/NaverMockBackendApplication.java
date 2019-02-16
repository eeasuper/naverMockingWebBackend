package com.nano.naver_m;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication

public class NaverMockBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(NaverMockBackendApplication.class, args);
	}
	@Autowired(required = true)
	public void configureJackson(ObjectMapper jackson2ObjectMapper) {
	    jackson2ObjectMapper.setVisibility(PropertyAccessor.ALL, Visibility.ANY);
	}
}

