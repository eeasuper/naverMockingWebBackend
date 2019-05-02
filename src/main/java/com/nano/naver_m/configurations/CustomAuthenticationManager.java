package com.nano.naver_m.configurations;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.nano.naver_m.exceptions.UserNotFoundException;
import com.nano.naver_m.models.SiteUser;
import com.nano.naver_m.repository.UserRepository;

@Component
public class CustomAuthenticationManager implements AuthenticationManager{

	@Autowired
	UserRepository repository;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
	    String username = authentication.getPrincipal() + "";
	    String password = authentication.getCredentials() + "";
	    
	    SiteUser user = repository.findByUsername(username).orElseThrow(() -> new UserNotFoundException((long) 1));;
	    if(user == null) {
	    	throw new BadCredentialsException("1000");
	    }
	    if(!passwordEncoder.matches(password, user.getPassword())) {
	    	throw new BadCredentialsException("1000");
	    }
	    return new UsernamePasswordAuthenticationToken(username, password, Collections.emptyList());
	}

}
