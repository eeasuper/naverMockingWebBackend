package com.nano.naver_m.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;import org.springframework.web.cors.CorsConfiguration;

import com.nano.naver_m.assemblers.UserResource;
import com.nano.naver_m.assemblers.UserResourceAssembler;
import com.nano.naver_m.exceptions.UserNotFoundException;
import com.nano.naver_m.models.User;
import com.nano.naver_m.repository.UserRepository;
import com.nano.naver_m.services.SignInService;
import com.nano.naver_m.services.SignUpService;
import com.nano.naver_m.services.TokenAuthenticationService;
@RestController
@CrossOrigin(origins = "https://naver-mock-app.herokuapp.com")
public class UserController {
	private final UserRepository repository;
	private final UserResourceAssembler assembler;
	Logger logger = LoggerFactory.getLogger(UserController.class);
	UserController(UserRepository repository, UserResourceAssembler assembler){
		this.repository = repository;
		this.assembler = assembler;
	}
	
	@Autowired
	SignUpService signupService;
	@Autowired
	SignInService signinService;
	
	@RequestMapping(method = RequestMethod.GET, value = "/api/users", produces = {MediaType.APPLICATION_JSON_VALUE})
	public
	Resources<Resource<User>> all(){
		List<Resource<User>> user = repository.findAll().stream().map(assembler::toResource)
				.collect(Collectors.toList());
		
		return new Resources<>(user,
					linkTo(methodOn(UserController.class).all()).withSelfRel());
				
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/register", produces = {MediaType.APPLICATION_JSON_VALUE})
	ResponseEntity<?> newUser(@RequestBody User newUser, HttpServletResponse res) throws URISyntaxException {
		//successful curl request:
		//curl -v localhost:8080/register --header "Accept: application/json" --header "Content-Type: application/json" --data "{\"name\":\"test1\",\"username\":\"testusername1\",\"password\":\"testpassword1\",\"email\":\"testemail1\", \"token\":\"testtoken\"}"
		//request format:
		/*
		 * {
		      username: username,
		      name: name,
		      email: email,
		      password: password
		    }
		 */
		User user = signupService.signup(res, newUser);
		
		
		Resource<User> resource = assembler.toResource(user);
		return ResponseEntity
				.created(new URI(resource.getId().expand().getHref()))
				.body(resource);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/login", produces = {MediaType.APPLICATION_JSON_VALUE})
	ResponseEntity<?> signIn(@RequestBody User newUser, HttpServletRequest req,  HttpServletResponse res) throws URISyntaxException{
		//successful curl request:
		//curl -v localhost:8080/login --header "Accept: application/json" --header "Content-Type: application/json" --data "{\"name\":\"name\",\"username\":\"username\",\"password\":\"password\",\"email\":\"email@email.com\", \"token\":\"testtoken\"}"
		//curl -v localhost:8080/login -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ1c2VybmFtZSJ9.xOXxbTzfz7eQKjacwVVkOhT0oodx58GX6Wi7e3aiM0Q" -H "Accept: application/json" --header "Content-Type: application/json" --user username:password --data "{\"name\":\"name\",\"username\":\"username\",\"password\":\"password\",\"email\":\"email@email.com\", \"token\":\"testtoken\"}"
		//https://stackoverflow.com/questions/22453550/custom-authentication-provider-not-being-called/22457561#22457561
		//curl -v localhost:8080/login -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ1c2VybmFtZSJ9.xOXxbTzfz7eQKjacwVVkOhT0oodx58GX6Wi7e3aiM0Q" -H "Accept: application/json" --header "Content-Type: application/x-www-form-urlencoded" --data "username=username&password=password"
		//https://stackoverflow.com/questions/31826233/custom-authentication-manager-with-spring-security-and-java-configuration
		/*
		 * request format:
		 * state = {
		      username: this.state.username,
		      password: this.state.password,
		      stayLoggedIn: this.state.stayLoggedIn
		    }
		 */

		logger.info("user:"+newUser.getUsername()+" "+ newUser.getPassword());
		User user = signinService.signIn(newUser.getUsername(), newUser.getPassword(), res);
//		User user = repository.findByUsername(newUser.getUsername()).orElseThrow(() -> new UserNotFoundException(id));;//This line is for Test

		Resource<User> resource = assembler.toResource(user);
		int status = res.getStatus();
		logger.info("processing...login status: " + status);
		if(status == 201 || status == 200) {
			return ResponseEntity
					.created(new URI(resource.getId().expand().getHref()))
					.body(resource);
		}else {
			return ResponseEntity.status(403).build();
		}
		
	}
	

	@RequestMapping(method = RequestMethod.GET, value = "/users/{username}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public Set<Boolean> checkValidUsername(@PathVariable String username) {
		User user = repository.findByUsername(username).orElseThrow(() -> new UserNotFoundException((long) 1));;
		boolean isValid = false;
		if(user != null) {
			isValid = false;
		}else if(user == null) {
			isValid = true;
		}
		Set<Boolean> response = Collections.singleton(isValid);
		return response;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/api/users/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public
	User one(@PathVariable Long id) {

		return repository.findById(id)
			.orElseThrow(() -> new UserNotFoundException(id));
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/api/users/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
	User replaceUser(@RequestBody User newUser, @PathVariable Long id) {

		return repository.findById(id)
			.map(user -> {
				user.setName(newUser.getName());
				user.setUsername(newUser.getUsername());
				user.setPassword(newUser.getPassword());
				user.setEmail(newUser.getEmail());
				return repository.save(user);
			})
			.orElseGet(() -> {
				newUser.setId(id);
				return repository.save(newUser);
			});
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/api/users/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
	void deleteUser(@PathVariable Long id) {
		repository.deleteById(id);
	}
	
}
