package com.nano.naver_m.assemblers;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.Link;

import com.nano.naver_m.controller.CartController;
import com.nano.naver_m.controller.UserController;
import com.nano.naver_m.models.OrderDetails;

@Component
public class OrderDetailsResourceAssembler extends ResourceAssemblerSupport<OrderDetails, OrderDetailsResource>{

	public OrderDetailsResourceAssembler() {
		super(CartController.class, OrderDetailsResource.class);
	}

	@Override
	public OrderDetailsResource toResource(OrderDetails orderdetails) {
		
		return null;
	}
	
	public Resources<Resource<OrderDetails>> toResources(List<OrderDetails> list, Long id){
		List<Link> links = new ArrayList<Link>();
		links.add(new Link("http://something"));
		List<Resource<OrderDetails>> resList = new ArrayList<Resource<OrderDetails>>();
		list.stream().forEach(val ->{
			Resource<OrderDetails> res = new Resource<OrderDetails>(val);
			resList.add(res);
		});
		Resources<Resource<OrderDetails>> resource = new Resources<>(resList,links);
		return resource;
	}
	
	
		
	
}
