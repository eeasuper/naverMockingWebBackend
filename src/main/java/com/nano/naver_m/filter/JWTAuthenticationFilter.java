package com.nano.naver_m.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import com.nano.naver_m.exceptions.UserNotFoundException;
import com.nano.naver_m.models.User;
import com.nano.naver_m.repository.UserRepository;
import com.nano.naver_m.services.TokenAuthenticationService;

@Component
//@Order(Ordered.HIGHEST_PRECEDENCE)
public class JWTAuthenticationFilter extends GenericFilterBean {
   
	@Autowired
	private UserRepository repository;
	
	
   @Override
   public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
           throws IOException, ServletException {
	   //Shouldn't comment this out for Rest API!...I don't know why...
       System.out.println("JWTAuthenticationFilter.doFilter");
       HttpServletRequest req = (HttpServletRequest) servletRequest;
       String uri = req.getRequestURI().toString();
       System.out.println(uri);
       HttpServletResponse res = (HttpServletResponse) servletResponse;
       res.setStatus(403);

       if(!uri.startsWith("/login") && !uri.startsWith("/register")) {
	       Authentication authentication = TokenAuthenticationService
	               .getAuthentication((HttpServletRequest) servletRequest);
	       //authentication here is a 'hollow' usernamepasswordtoken with just the username set as authentication.
	       System.out.println("JWTAuthenticationFilter.doFilter repository search");
	       User user = repository.findByUsername(authentication.getName()).orElseThrow(() -> new UserNotFoundException((long) 1));;
	       System.out.println("JWTAuthenticationFilter.doFilter repository serach finished");
	       if(user != null) {
	    	   SecurityContextHolder.getContext().setAuthentication(authentication);
	       }
	       res.setStatus(401);
       }
       ServletResponse newRes = (ServletResponse) res;
       filterChain.doFilter(servletRequest, newRes);
   }
}
