package com.nano.naver_m.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nano.naver_m.assemblers.OrderDetailsResource;
import com.nano.naver_m.assemblers.OrderDetailsResourceAssembler;
import com.nano.naver_m.exceptions.ProductNotFoundException;
import com.nano.naver_m.exceptions.UserNotFoundException;
import com.nano.naver_m.models.OrderDetails;
import com.nano.naver_m.models.Product;
import com.nano.naver_m.repository.CartRepository;
import com.nano.naver_m.repository.ProductRepository;
import com.nano.naver_m.repository.UserRepository;
import com.nano.naver_m.services.CartService;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
@RestController
public class CartController {
	private final CartRepository repository;
	private final OrderDetailsResourceAssembler assembler;
	private final UserRepository userRepository;
	private final ProductRepository productRepository;
	@Autowired
	CartService cartService;
//	@Autowired ProductRepository productRepository;
	
	Logger logger = LoggerFactory.getLogger(CartController.class);
	public CartController(CartRepository repository, OrderDetailsResourceAssembler assembler, UserRepository userRepository, ProductRepository productRepository) {
		super();
		this.repository = repository;
		this.assembler = assembler;
		this.userRepository = userRepository;
		this.productRepository = productRepository;
	}
	
	//"produces" parameter is important. Without it Spring does not activate jackson and tries to convert resource as XML.
	@RequestMapping(method = RequestMethod.GET, value = "/users/{id}/cart", produces = {MediaType.APPLICATION_JSON_VALUE})
	public Resources<Resource<OrderDetails>> allFromUser(@PathVariable Long id){
//	public ResponseEntity<?> allFromUser(@PathVariable Long id){
		List<OrderDetails> cart = repository.findByUserIdOrderByIdAsc(id);
		Resources<Resource<OrderDetails>> resource = assembler.toResources(cart,id);
		return resource;
//		return ResponseEntity.status(HttpStatus.ACCEPTED).build();
	}

	//do one mapping for one product in cart.	
//	@GetMapping("/orders/{id}")
//	public Resource<OrderDetails> one(@PathVariable("id") Long id){
//		return assembler.toResource(repository.findById(id)
//				.orElseThrow(()-> new OrderDetailsNotFoundException(id)));
//	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/users/{id}/cart", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<OrderDetailsResource>addToCart(@RequestBody OrderDetails newOrder){
		OrderDetails order = cartService.addToCart(newOrder);

		return ResponseEntity
				//change allFromUser to one() when one() is created...
				.created(linkTo(methodOn(CartController.class).allFromUser(order.getUserId())).toUri())
				.body(assembler.toResource(order));
	}


	//if possible create enums and add them to OrderDetails.java to cancel the order and not delete it.
	@Transactional 
	@RequestMapping(method = RequestMethod.DELETE, value = "/users/{id}/cart/{id2}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<OrderDetailsResource> deleteFromCart(@PathVariable("id") Long user_id, @PathVariable("id2") Long product_id){
		Long id = repository.deleteByUserIdAndProductId(user_id, product_id);
		if(id != null) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		else{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			//could actually be a different error, but I'll just go with this.
		}
	}
}
