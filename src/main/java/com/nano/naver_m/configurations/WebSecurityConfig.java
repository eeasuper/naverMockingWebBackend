package com.nano.naver_m.configurations;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.nano.naver_m.repository.UserRepository;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
//
//	@Bean
//	public CorsConfigurationSource corsConfigurationSource() {
//		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		CorsConfiguration config = new CorsConfiguration();
//		config.setAllowCredentials(true);
//		config.addAllowedOrigin("http://localhost:3000");
//		List<String> headers = new ArrayList<>();
//		headers.add("content-type");
//		headers.add("Origin");
//		config.setAllowedHeaders(headers);
//		config.setMaxAge((long) 86400);
//		config.addAllowedMethod("POST OPTIONS");
//		source.registerCorsConfiguration("/**", config);
//		return source;
//	}
//    
    protected void configure(HttpSecurity http) throws Exception {
    	//addFilterBefore(corsFilter(), HeaderFilter.class)
        http.cors().and().csrf().disable().authorizeRequests()
                // No need authentication.
                .antMatchers("/").permitAll()
                .antMatchers(HttpMethod.POST, "/login").permitAll() //
                .antMatchers(HttpMethod.GET, "/login").permitAll() // For Test on Browser
                .antMatchers(HttpMethod.POST, "/register").permitAll()
                .antMatchers(HttpMethod.GET, "/users/1/cart").permitAll()
                .anyRequest().permitAll() // this line is for test.
                .and().headers().frameOptions().disable();
//                .and().headers()                
                // Need authentication.
//                .anyRequest().authenticated()
        http.headers().frameOptions().disable();
    }
// 
//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//        return bCryptPasswordEncoder;
//    }
// 
////    @Override
////    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//// 
////        String password = "123";
//// 
////        String encrytedPassword = this.passwordEncoder().encode(password);
////        System.out.println("Encoded password of 123=" + encrytedPassword);
//// 
////        InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder> //
////        mngConfig = auth.inMemoryAuthentication();
//// 
////        // Defines 2 users, stored in memory.
////        // ** Spring BOOT >= 2.x (Spring Security 5.x)
////        // Spring auto add ROLE_
////        UserDetails u1 = User.withUsername("tom").password(encrytedPassword).roles("USER").build();
////        UserDetails u2 = User.withUsername("jerry").password(encrytedPassword).roles("USER").build();
//// 
////        mngConfig.withUser(u1);
////        mngConfig.withUser(u2);
//// 
////        // If Spring BOOT < 2.x (Spring Security 4.x)):
////        // Spring auto add ROLE_
////        // mngConfig.withUser("tom").password("123").roles("USER");
////        // mngConfig.withUser("jerry").password("123").roles("USER");
//// 
////    }
//     
}
