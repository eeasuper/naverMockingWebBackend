package com.nano.naver_m.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

//@Component
@Configuration
public class HeaderFilter  {
//	implements Filter
	Logger logger = LoggerFactory.getLogger(HeaderFilter.class);
//	@Bean
//	public FilterRegistrationBean<CorsFilter> corsFilter() {
//		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		CorsConfiguration config = new CorsConfiguration();
//		config.setAllowCredentials(true);
//		config.addAllowedOrigin("localhost:3000");
//		config.addAllowedHeader("*");
//		config.addAllowedMethod("*");
//		source.registerCorsConfiguration("/**", config);
//		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>(new CorsFilter((org.springframework.web.cors.CorsConfigurationSource) source));
//		bean.setOrder(0);
//		return bean;
//	}
//	   @Override
//	    public void init(FilterConfig filterConfig) throws ServletException {
//
//	    }

//	    @Override
//	    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//	        HttpServletResponse response = (HttpServletResponse) servletResponse;
//	        HttpServletRequest request= (HttpServletRequest) servletRequest;
//
//	        response.setHeader("Access-Control-Allow-Origin", "*");
//	        response.setHeader("Access-Control-Allow-Methods", "GET,POST,DELETE,PUT,OPTIONS");
//	        response.setHeader("Access-Control-Allow-Headers", "*");
////	        response.setHeader("Access-Control-Allow-Credentials", true);
////	        response.setHeader("Access-Control-Max-Age", 180);
//	        filterChain.doFilter(servletRequest, servletResponse);
//	    }

//	    @Override
//	    public void destroy() {
//
//	    }
}
