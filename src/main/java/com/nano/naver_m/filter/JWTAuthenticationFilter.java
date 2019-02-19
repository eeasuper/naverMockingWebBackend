package com.nano.naver_m.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestWrapper;
import javax.servlet.ServletResponse;
import javax.servlet.ServletResponseWrapper;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import com.nano.naver_m.models.User;
import com.nano.naver_m.repository.UserRepository;
import com.nano.naver_m.services.TokenAuthenticationService;

@Component
//@Order(Ordered.HIGHEST_PRECEDENCE)
public class JWTAuthenticationFilter extends GenericFilterBean {
   
	
   @Override
   public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
           throws IOException, ServletException {
	   //Shouldn't comment this out for Rest API!...I don't know why...
       System.out.println("JWTAuthenticationFilter.doFilter");
       
       Authentication authentication = TokenAuthenticationService
               .getAuthentication((HttpServletRequest) servletRequest);
       //authentication here is a 'hollow' usernamepasswordtoken with just the username set as authentication.
       SecurityContextHolder.getContext().setAuthentication(authentication);
        
       filterChain.doFilter(servletRequest, servletResponse);
   }
}
