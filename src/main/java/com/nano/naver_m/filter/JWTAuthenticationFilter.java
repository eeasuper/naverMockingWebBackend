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
import com.nano.naver_m.models.User;
import com.nano.naver_m.repository.UserRepository;
import com.nano.naver_m.services.TokenAuthenticationService;

//@Component
//@Order(Ordered.HIGHEST_PRECEDENCE)
public class JWTAuthenticationFilter implements Filter {
   
//	@Autowired
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
	   //Shouldn't comment this out for Rest API!...I don't know why...
       System.out.println("JWTAuthenticationFilter.doFilter");
       HttpServletRequest req = (HttpServletRequest) servletRequest;
       String uri = req.getRequestURI().toString();
       System.out.println(uri);
       HttpServletResponse res = (HttpServletResponse) servletResponse;
//       res.setStatus(401);
       if(!checkAuthIsNotRequired(uri)) {
//    	   res.setStatus(404);
    	   System.out.println("going through auth");
	       Authentication authentication = TokenAuthenticationService
	               .getAuthentication((HttpServletRequest) servletRequest);
	       //authentication here is a 'hollow' usernamepasswordtoken with just the username set as authentication.
	       System.out.println("JWTAuthenticationFilter.doFilter repository search");
	       boolean exists = repository.existsByUsername(authentication.getName());
	       System.out.println(exists);
	       System.out.println("JWTAuthenticationFilter.doFilter repository serach finished");
	       if(exists) {
	    	   SecurityContextHolder.getContext().setAuthentication(authentication);
	       }
       }
       System.out.println(res.getStatus());
//       ServletResponse newRes = (ServletResponse) res;
//       filterChain.doFilter(servletRequest, newRes);
       filterChain.doFilter(servletRequest, servletResponse);
   }
   
   @Override
   public void init(FilterConfig filterConfig) {
   }

   @Override
   public void destroy() {
   }
}
