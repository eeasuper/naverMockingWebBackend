package com.nano.naver_m.configurations;




import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.nano.naver_m.filter.JWTAuthenticationFilter;
import com.nano.naver_m.filter.JWTLoginFilter;



@Configuration
@EnableWebSecurity
@ComponentScan("com.nano.naver_m.configurations")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	//The commented code below should only be uncommented when /login should be mapped to JWTLoginFilter.
//	@Autowired
//	private CustomAuthenticationProvider authenticationProvider;

	
//	@Autowired JWTLoginFilter loginFilter;
	
	
//	@Autowired AuthenticationManager authMan;
//	@Autowired
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		System.out.println("get called already!");
//    	//for jdbcAuthentications:
////    	auth.jdbcAuthentication()...
//    	//for userDetailAuthentications:
////    	auth.userDetailsService(userProvider);
////    	auth.authenticationProvider(new CustomAuthenticationProvider());
//    	auth.authenticationProvider(authenticationProvider);
//    }   
//	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
        http.cors().and().csrf().disable()
        		.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers(HttpMethod.POST, "/login").permitAll() 
                .antMatchers(HttpMethod.GET, "/login").permitAll() // For Test on Browser
                .antMatchers(HttpMethod.POST, "/register").permitAll()
//                .antMatchers("/users/**").permitAll() //For Test
//                .antMatchers("/api/**").permitAll() //For Test
//                .anyRequest().permitAll() // this line is for test.
                .anyRequest().authenticated()

                .and().headers().frameOptions().disable()
                .and()
        		//The filter below should only be uncommented when /login should be mapped to JWTLoginFilter.
//                .addFilterBefore(new JWTLoginFilter("/login", authenticationManager()),
//                        UsernamePasswordAuthenticationFilter.class) // this line is commented out for reasons written in the JWTLoginFilter.class
                .addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    }

}
