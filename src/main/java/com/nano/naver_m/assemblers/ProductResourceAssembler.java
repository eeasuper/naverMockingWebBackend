package com.nano.naver_m.assemblers;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import com.nano.naver_m.controller.ProductController;
import com.nano.naver_m.models.Product;

@Component
public class ProductResourceAssembler implements ResourceAssembler<Product, Resource<Product>>{

	@Override
	public Resource<Product> toResource(Product entity) {
		Resource<Product> resource = new Resource<>(entity,
				linkTo(methodOn(ProductController.class).one(entity.getId())).withSelfRel(),
				linkTo(methodOn(ProductController.class).all()).withRel("api/products"));
		return resource;
	}

}
