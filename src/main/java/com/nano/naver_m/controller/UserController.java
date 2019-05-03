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
import org.springframework.validation.annotation.Validated;
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
import com.nano.naver_m.models.SiteUser;
import com.nano.naver_m.repository.UserRepository;
import com.nano.naver_m.services.SignInService;
import com.nano.naver_m.services.SignUpService;
import com.nano.naver_m.services.TokenAuthenticationService;
@RestController
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
	Resources<Resource<SiteUser>> all(){
		List<Resource<SiteUser>> user = repository.findAll().stream().map(assembler::toResource)
				.collect(Collectors.toList());
		
		return new Resources<>(user,
					linkTo(methodOn(UserController.class).all()).withSelfRel());
				
	}
	
	//In a full app, you would validate username and password in BOTH front-end and back-end. However, I skip validation in backend.
	@RequestMapping(method = RequestMethod.POST, value = "/register", produces = {MediaType.APPLICATION_JSON_VALUE})
	ResponseEntity<?> newUser(@RequestBody SiteUser newUser, HttpServletResponse res) throws URISyntaxException {
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
		SiteUser user = signupService.signup(res, newUser);
		
		Resource<SiteUser> resource = assembler.toResource(user);
		return ResponseEntity
				.created(new URI(resource.getId().expand().getHref()))
				.body(resource);
	}
	
	//maybe create a mapping for /error next time?
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/login", produces = {MediaType.APPLICATION_JSON_VALUE})
	ResponseEntity<?> signIn(@RequestBody SiteUser newUser, HttpServletRequest req,  HttpServletResponse res) throws URISyntaxException{
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
		SiteUser user = signinService.signIn(newUser.getUsername(), newUser.getPassword(), res);

		Resource<SiteUser> resource = assembler.toResource(user);
		int status = res.getStatus();
		if(status == 201 || status == 200) {
			return ResponseEntity
					.created(new URI(resource.getId().expand().getHref()))
					.body(resource);
		}else {
			return ResponseEntity.status(status).build();
		}

	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/users/validate", produces = {MediaType.APPLICATION_JSON_VALUE})
	public Set<Boolean> checkValidUsername(HttpServletRequest req, HttpServletResponse res) {
		//curl to test with:
		//curl -v -X POST localhost:8080/users/validate --header "Content-Type: application/x-www-form-urlencoded" -d "username=username"
		String username = req.getParameter("username");
		boolean isValid = repository.existsByUsername(username);
		
		Set<Boolean> response = Collections.singleton(isValid);
		return response;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/api/users/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public
	SiteUser one(@PathVariable Long id) {

		return repository.findById(id)
			.orElseThrow(() -> new UserNotFoundException(id));
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/api/users/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
	SiteUser replaceUser(@RequestBody SiteUser newUser, @PathVariable Long id) {

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
