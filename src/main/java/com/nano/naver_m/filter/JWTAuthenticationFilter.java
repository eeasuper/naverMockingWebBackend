package com.nano.naver_m.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.nano.naver_m.exceptions.UserNotFoundException;
import com.nano.naver_m.models.SiteUser;
import com.nano.naver_m.repository.UserRepository;
import com.nano.naver_m.services.TokenAuthenticationService;


public class JWTAuthenticationFilter implements Filter {
   
	private UserRepository repository;
	
	public JWTAuthenticationFilter(UserRepository repository){
		this.repository = repository;
	}
	
	@SuppressWarnings("serial")
	private static final List<String> urlsNotRequiringAuth = new ArrayList<String>() {{
		add("/login");
		add("/register");
		add("/users/validate");
	}};
	
	
	private static boolean checkAuthIsNotRequired(String uri) {
		return urlsNotRequiringAuth.stream().anyMatch(uri::equals);
	}
	
   @Override
   public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
           throws IOException, ServletException {

       HttpServletRequest req = (HttpServletRequest) servletRequest;
       String uri = req.getRequestURI().toString();
       HttpServletResponse res = (HttpServletResponse) servletResponse;
       if(!checkAuthIsNotRequired(uri)) {
	       Authentication authentication = TokenAuthenticationService
	               .getAuthentication((HttpServletRequest) servletRequest);
	       boolean exists = repository.existsByUsername(authentication.getName());
	       if(exists) {
	    	   SecurityContextHolder.getContext().setAuthentication(authentication);
	       }
       }
       filterChain.doFilter(servletRequest, servletResponse);
   }
   
   @Override
   public void init(FilterConfig filterConfig) {
   }

   @Override
   public void destroy() {
   }
}
