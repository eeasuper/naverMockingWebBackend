package com.nano.naver_m.services;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.nano.naver_m.models.SiteUser;
import com.nano.naver_m.repository.UserRepository;

@Component
public class SignUpService {
	@Autowired
	UserRepository repository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	SignUpService(UserRepository repository){
		this.repository = repository;
	}
    
	public SiteUser signup(HttpServletResponse res, SiteUser user){
		String encryptedPassword = this.passwordEncoder.encode(user.getPassword());
		user.setPassword(encryptedPassword);
		repository.save(user);
		//I think there's no need to set password as the encrypted password and send it as response.
		user.setPassword(null);
		return user;
	}
		
}
