package com.nano.naver_m.configurations;




import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.nano.naver_m.filter.JWTAuthenticationFilter;
import com.nano.naver_m.repository.UserRepository;



@Configuration
@EnableWebSecurity
@ComponentScan("com.nano.naver_m.configurations")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter implements CorsConfigurationSource{
	//The commented code below should only be uncommented when /login should be mapped to JWTLoginFilter.
//	@Autowired
//	private CustomAuthenticationProvider authenticationProvider;

	
//	@Autowired JWTLoginFilter loginFilter;
	@Autowired UserRepository repository;
	
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
//	@Bean
////	public FilterRegistrationBean corsFilter() {
//	public CorsConfiguration corsConfigurationSource() {
//		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		CorsConfiguration config = new CorsConfiguration();
//		config.setAllowCredentials(true);
//		config.addAllowedOrigin("https://naver-mock-app.herokuapp.com/");
//		config.addAllowedHeader("*");
//		config.addAllowedMethod("*");
////		source.registerCorsConfiguration("/**", config);
//		CorsConfigurationSource sss = new CorsConfigurationSource(config);
//		return config;
////		FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source.getCorsConfiguration(exchange)));
////		bean.setOrder(0);
////		return bean;
//	}
	private final String frontEndDomain = "https://naver-mock-app.herokuapp.com";
	 @Bean
	    public WebMvcConfigurer corsConfigurer() {
	        return new WebMvcConfigurer() {
	            @Override
	            public void addCorsMappings(CorsRegistry registry) {
	                registry.addMapping("/users/*/cart/**").allowedOrigins(frontEndDomain)
	            		.allowedMethods("DELETE", "POST", "GET");
	                registry.addMapping("/register").allowedOrigins(frontEndDomain)
	                	.allowedMethods("POST");
	                registry.addMapping("/login").allowedOrigins(frontEndDomain)
	            		.allowedMethods("POST");
	                registry.addMapping("/api/users/**").allowedOrigins(frontEndDomain)
	            		.allowedMethods("POST", "GET", "PUT", "DELETE");
	                registry.addMapping("/users/*").allowedOrigins(frontEndDomain)
	            		.allowedMethods("GET");
	                registry.addMapping("/api/products/**").allowedOrigins(frontEndDomain)
	            		.allowedMethods("GET");
	            }
	        };
	}
	
    @Override
//    @DependsOn("corsConfigurer")
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
                .addFilterBefore(new JWTAuthenticationFilter(repository), UsernamePasswordAuthenticationFilter.class);

    }

	@Override
	@Bean
	public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("https://naver-mock-app.herokuapp.com");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		return config;
	}

}
