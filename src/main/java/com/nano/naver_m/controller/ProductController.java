package com.nano.naver_m.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nano.naver_m.assemblers.ProductResourceAssembler;
import com.nano.naver_m.exceptions.ProductNotFoundException;
import com.nano.naver_m.models.Product;
import com.nano.naver_m.repository.ProductRepository;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@RestController
public class ProductController {
	private final ProductRepository repository;
	private final ProductResourceAssembler assembler;
	
	public ProductController(ProductRepository repository, ProductResourceAssembler assembler) {
		super();
		this.repository = repository;
		this.assembler = assembler;
	}
	
	//"produces" parameter is important. Without it Spring does not activate jackson and tries to convert resource as XML.
	@RequestMapping(method = RequestMethod.GET, value = "/api/products", produces = {MediaType.APPLICATION_JSON_VALUE})
	public 
	Resources<Resource<Product>> all(){
		//successful authentication and retrieval of api (this example can be used for others):
		//curl -v localhost:8080/api/products -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ1c2VybmFtZSJ9.xOXxbTzfz7eQKjacwVVkOhT0oodx58GX6Wi7e3aiM0Q"
		List<Resource<Product>> products = repository.findAll().stream()
				.map(assembler :: toResource)
				.collect(Collectors.toList());
		
		return new Resources<>(products,
				linkTo(methodOn(ProductController.class).all()).withSelfRel());
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/api/products/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public Resource<Product> one(@PathVariable Long id) {
		return assembler.toResource(repository.findById(id)
				.orElseThrow(()-> new ProductNotFoundException(id)));
	}
	
	
}
