package com.nano.naver_m.configurations;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.nano.naver_m.exceptions.UserNotFoundException;
import com.nano.naver_m.models.SiteUser;
import com.nano.naver_m.repository.UserRepository;

//This class does not get activated because of reasons written in JWTLoginFilter.class

//@Component
public class CustomAuthenticationProvider implements AuthenticationProvider{
	Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);
	@Autowired
	UserRepository repository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		System.out.println("CustomAuthenticationProvider authenticate()");
		SiteUser user = repository.findByUsername(authentication.getName()).orElseThrow(() -> new UserNotFoundException((long) 1));;
		boolean authenticated = user != null ? true : false;  
		boolean matches = this.passwordEncoder.matches((String) authentication.getCredentials(), user.getPassword());
		if(!matches || user == null) {
			throw new BadCredentialsException("1000");
		}
		if(authenticated && matches) {
			return new UsernamePasswordAuthenticationToken(authentication.getName(), authentication.getCredentials(),new ArrayList<>());
		}
		return null;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
