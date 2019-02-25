package com.nano.naver_m.configurations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.nano.naver_m.models.OrderDetails;
import com.nano.naver_m.models.Product;
import com.nano.naver_m.models.User;
import com.nano.naver_m.repository.CartRepository;
import com.nano.naver_m.repository.ProductRepository;
import com.nano.naver_m.repository.UserRepository;

@Configuration
public class LoadDatabase {
	Logger logger = LoggerFactory.getLogger(LoadDatabase.class);
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	@Bean
	CommandLineRunner initDatabase(UserRepository userRepository, ProductRepository productRepository, CartRepository cartRepository) {
		logger.info("Preloading user");
		String encryptedPassword = this.passwordEncoder.encode("password");
		System.out.println("encryptedPassword: "+ encryptedPassword);
		User user = userRepository.save(new User("name","username", encryptedPassword ,"email@email.com"));
		logger.info("Preloading products");
		Product product = productRepository.save(new Product("[대구백화점 1관] [시에로코스메틱]유니 어 데이",20000));
		Product product2 = productRepository.save(new Product("[PUPA] 멀티플레이 트리플 퍼포즈 아이펜슬 5종",22000));
		productRepository.save(new Product("[시세이도] 맨 토탈 리바이탈라이저 라이트 플루이드",69700));
		productRepository.save(new Product("[비디비치](신세계강남점)[2월] 퍼펙트 브이 핏 쿠션 (정품 픽서 증정)",48700));
		productRepository.save(new Product("[시세이도] 트렌스루센트 프레스드 파우더",46750));
		productRepository.save(new Product("[시세이도] 맨 클렌징 폼",25500));
		cartRepository.save(new OrderDetails(user,product,1));
		cartRepository.save(new OrderDetails(user,product2,1));
		return null;
	}
}
