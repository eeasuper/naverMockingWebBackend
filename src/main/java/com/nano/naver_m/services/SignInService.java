package com.nano.naver_m.services;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.nano.naver_m.models.User;
import com.nano.naver_m.repository.UserRepository;

@Component
public class SignInService {
	@Autowired
	UserRepository repository;
	private BCryptPasswordEncoder passwordEncoder;
	
	
	public User signIn(String username, String password){
		User user = repository.findByUsername(username);
		//first param of matches is RAW password
		//turning off below when security is off. uncomment later.
		boolean matches = this.passwordEncoder.matches(password, user.getPassword());
		user.setPassword(null);
		return matches ? user:null;
//		return user;
	}
}
