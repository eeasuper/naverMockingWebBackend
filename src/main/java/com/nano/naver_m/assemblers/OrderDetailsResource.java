package com.nano.naver_m.assemblers;

import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import com.nano.naver_m.models.OrderDetails;
import com.nano.naver_m.models.Product;
import com.nano.naver_m.models.User;

public class OrderDetailsResource extends ResourceSupport{
	
	OrderDetails order;

	public OrderDetailsResource(OrderDetails order) {
		super();
		this.order = order;
	}
	
}
