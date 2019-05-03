package com.nano.naver_m.services;

import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.nano.naver_m.controller.UserController;
import com.nano.naver_m.exceptions.UserNotFoundException;
import com.nano.naver_m.models.SiteUser;
import com.nano.naver_m.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class SignInService {
	Logger logger = LoggerFactory.getLogger(SignInService.class);
	
	@Autowired
	UserRepository repository;
	@Autowired
	TokenAuthenticationService tokenService;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

    
	public SiteUser signIn(String username, String password, HttpServletResponse res){
		//https://blog.restcase.com/rest-api-error-codes-101/
		
//		String usernameFromToken = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody()
//                .getSubject();
		SiteUser user = repository.findByUsername(username).orElseThrow(() -> new UserNotFoundException((long) 1));;
		
		boolean exists = user != null ? true : false;
		boolean matches = this.passwordEncoder.matches(password, user.getPassword());
		
		if(!matches || !exists) {
			System.out.println("credentials error");
			res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return user;
		}
		
		if(matches && exists) {
			SiteUser u=tokenService.addToken(username, password, user);
			u.setPassword(null);
			return u;
		}
		user.setPassword(null);
		return user;
	}
}
