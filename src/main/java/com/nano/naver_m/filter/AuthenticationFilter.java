//package com.nano.naver_m.filter;
//
//import java.io.IOException;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.core.annotation.Order;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
//import org.springframework.security.web.util.matcher.RequestMatcher;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import org.springframework.web.server.WebFilter;
//import org.springframework.web.server.WebFilterChain;
//
//import com.nano.naver_m.configurations.LoadDatabase;
//
//import reactor.core.publisher.Mono;
//
////@Component
////@Order(1)
//public class AuthenticationFilter {
//	//https://daveceddia.com/access-control-allow-origin-cors-errors-in-react-express/
////https://enable-cors.org/server_spring-boot_kotlin.html
//	Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);
//	
////	@Override
////	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
////		logger.info("went through filter");
////		if(exchange !=null) {
////			exchange.getResponse().getHeaders().add("Access-Control-Allow-Origin", "localhost:3000");
////		}
////		chain.filter(exchange);
////		
////		return null;
////	}
//
//
//	
//}

//implements WebFilter