package com.nano.naver_m;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nano.naver_m.models.OrderDetails;
import com.nano.naver_m.models.Product;
import com.nano.naver_m.models.SiteUser;
import com.nano.naver_m.repository.CartRepository;
import com.nano.naver_m.repository.ProductRepository;
import com.nano.naver_m.repository.UserRepository;

@SpringBootApplication
public class NaverMockBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(NaverMockBackendApplication.class, args);
		
	}
	@Autowired(required = true)
	public void configureJackson(ObjectMapper jackson2ObjectMapper) {
	    jackson2ObjectMapper.setVisibility(PropertyAccessor.ALL, Visibility.ANY);
	}

	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	@Autowired
	UserRepository userRepository;
	@Autowired
	CartRepository cartRepository;
	@Autowired
	ProductRepository productRepository;
	@Bean
	CommandLineRunner init() {
		return(args)->{
			if(!userRepository.existsById((long)1)){
				String encryptedPassword = this.passwordEncoder.encode("password");
				SiteUser user = userRepository.save(new SiteUser("name","username", encryptedPassword ,"email@email.com"));
				Product product = productRepository.save(new Product("[대구백화점 1관] [시에로코스메틱]유니 어 데이",20000));
				Product product2 = productRepository.save(new Product("[PUPA] 멀티플레이 트리플 퍼포즈 아이펜슬 5종",22000));
				productRepository.save(new Product("[시세이도] 맨 토탈 리바이탈라이저 라이트 플루이드",69700));
				productRepository.save(new Product("[비디비치](신세계강남점)[2월] 퍼펙트 브이 핏 쿠션 (정품 픽서 증정)",48700));
				productRepository.save(new Product("[시세이도] 트렌스루센트 프레스드 파우더",46750));
				productRepository.save(new Product("[시세이도] 맨 클렌징 폼",25500));
				productRepository.save(new Product("test",25500));
				cartRepository.save(new OrderDetails((long) 1,(long)2,1));
				cartRepository.save(new OrderDetails((long) 1,(long)4,1));
			}
		};
	}
}

