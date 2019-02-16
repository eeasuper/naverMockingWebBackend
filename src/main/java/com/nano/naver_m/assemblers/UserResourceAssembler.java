package com.nano.naver_m.assemblers;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import com.nano.naver_m.controller.UserController;
import com.nano.naver_m.models.User;

@Component
public class UserResourceAssembler implements ResourceAssembler<User, Resource<User>> {


	@Override
	public Resource<User> toResource(User user) {
		Resource<User> resource = new Resource<>(user, 
					linkTo(methodOn(UserController.class).one(user.getId())).withSelfRel(),
					linkTo(methodOn(UserController.class).all()).withRel("api/users"));

		return resource;
	}

	
}
