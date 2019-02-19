package com.nano.naver_m.filter;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import com.nano.naver_m.models.User;
import com.nano.naver_m.repository.UserRepository;
import com.nano.naver_m.services.TokenAuthenticationService;

//This class overrides the /login requestMapping in UserController.class. Thus, this class is not implemented in WebSecurityConfig.
//Instead of this class, authentication is implemented in SignInService.class

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter{
	
    public JWTLoginFilter(String url, AuthenticationManager authManager) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
    }
    
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
    	System.out.println("attemptAuthentication");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
//        boolean authenticated = user != null ? true : false;
        boolean authenticated = true;
        System.out.printf("JWTLoginFilter.attemptAuthentication: username/password/authenticated= %s,%s,%s", username, password, authenticated);
        
        //not sure if you are allowed to return null...
        return authenticated ? getAuthenticationManager()
                .authenticate(new UsernamePasswordAuthenticationToken(username, password, Collections.emptyList())): null;
    }
 
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        // Write Authorization to Headers of Response.
        TokenAuthenticationService.addAuthentication(response, authResult.getName());
        
        if(authResult != null) {
	        String authorizationString = response.getHeader("Authorization");
	        System.out.println("JWTLoginFilter.successfulAuthentication:");
	        System.out.println("Authorization String=" + authorizationString);
        }
    }
 
}
