package com.nano.naver_m.configurations;




import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.nano.naver_m.filter.JWTAuthenticationFilter;
//import com.nano.naver_m.filter.testFilter;
import com.nano.naver_m.filter.testFilter;
import com.nano.naver_m.repository.UserRepository;



@Configuration
@EnableWebSecurity
@ComponentScan("com.nano.naver_m.configurations")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	//The commented code below should only be uncommented when /login should be mapped to JWTLoginFilter.
//	@Autowired
//	private CustomAuthenticationProvider authenticationProvider;

	public UserRepository repository;
	WebSecurityConfig(UserRepository repository){
		this.repository = repository;
	}
	
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
	private final String frontEndDomain = "https://naver-mock-app.herokuapp.com";
//	 @Bean
//	    public WebMvcConfigurer corsConfigurer() {
//	        return new WebMvcConfigurer() {
//	            @Override
//	            public void addCorsMappings(CorsRegistry registry) {
//	                registry.addMapping("/users/*/cart/**").allowedOrigins(frontEndDomain)
//	            		.allowedMethods("DELETE", "POST", "GET");
//	                registry.addMapping("/register").allowedOrigins(frontEndDomain)
//	                	.allowedMethods("POST");
//	                registry.addMapping("/login").allowedOrigins(frontEndDomain)
//	            		.allowedMethods("POST");
//	                registry.addMapping("/api/users/**").allowedOrigins(frontEndDomain)
//	            		.allowedMethods("POST", "GET", "PUT", "DELETE");
//	                registry.addMapping("/users/*").allowedOrigins(frontEndDomain)
//	            		.allowedMethods("GET");
//	                registry.addMapping("/api/products/**").allowedOrigins(frontEndDomain)
//	            		.allowedMethods("GET");
//	            }
//	        };
//	}
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//.csrf().disable()
        http.cors().and().csrf().disable()
        		.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/login").permitAll() 
//                .antMatchers(HttpMethod.GET, "/login").permitAll() // For Test on Browser
                .antMatchers(HttpMethod.POST, "/register").permitAll()
                .antMatchers(HttpMethod.POST, "/users/validate").permitAll()
//                .antMatchers(HttpMethod.POST, "/users/*/cart").
//                .antMatchers("/users/**").permitAll() //For Test
//                .antMatchers("/api/**").permitAll() //For Test
//                .anyRequest().permitAll() // this line is for test.
                .anyRequest().authenticated()

                .and().headers().frameOptions().disable()
                .and()
                .addFilterBefore(new testFilter(), ChannelProcessingFilter.class)
        		//The filter below should only be uncommented when /login should be mapped to JWTLoginFilter.
//                .addFilterBefore(new JWTLoginFilter("/login", authenticationManager()),
//                        UsernamePasswordAuthenticationFilter.class) // this line is commented out for reasons written in the JWTLoginFilter.class
                .addFilterBefore(new JWTAuthenticationFilter(repository), UsernamePasswordAuthenticationFilter.class);

    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(frontEndDomain));
        configuration.setAllowedMethods(Arrays.asList("GET","POST","DELETE"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }


//	@Override
//	public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
//		CorsConfiguration config = new CorsConfiguration();
//		config.setAllowCredentials(true);
//		config.addAllowedOrigin("https://naver-mock-app.herokuapp.com");
//		config.addAllowedHeader("*");
//		config.addAllowedMethod("*");
//		return config;
//	}

}
