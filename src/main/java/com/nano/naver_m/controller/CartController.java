package com.nano.naver_m.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nano.naver_m.assemblers.OrderDetailsResource;
import com.nano.naver_m.assemblers.OrderDetailsResourceAssembler;
import com.nano.naver_m.exceptions.UserNotFoundException;
import com.nano.naver_m.models.OrderDetails;
import com.nano.naver_m.repository.CartRepository;
import com.nano.naver_m.repository.UserRepository;
//import static org.sfw.hateoas.mvc.ControllerLinkBuilder.*;

@RestController
public class CartController {
	private final CartRepository repository;
	private final OrderDetailsResourceAssembler assembler;
	private final UserRepository userRepository;
	Logger logger = LoggerFactory.getLogger(CartController.class);
	public CartController(CartRepository repository, OrderDetailsResourceAssembler assembler, UserRepository userRepository) {
		super();
		this.repository = repository;
		this.assembler = assembler;
		this.userRepository = userRepository;
	}
	
	//"produces" parameter is important. Without it Spring does not activate jackson and tries to convert resource as XML.
	@RequestMapping(method = RequestMethod.GET, value = "/users/{id}/cart", produces = {MediaType.APPLICATION_JSON_VALUE})
	public Resources<Resource<OrderDetails>> allFromUser(@PathVariable Long id){
		List<OrderDetails> cart = repository.findByUserOrderByIdAsc(userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException(id)));
		Resources<Resource<OrderDetails>> resource = assembler.toResources(cart,id);
		return resource;
	}

	
//	@GetMapping("/orders/{id}")
//	public Resource<OrderDetails> one(@PathVariable("id") Long id){
//		return assembler.toResource(repository.findById(id)
//				.orElseThrow(()-> new OrderDetailsNotFoundException(id)));
//	}
	
	//do post mapping
	//do one mapping for one product in cart.
}
