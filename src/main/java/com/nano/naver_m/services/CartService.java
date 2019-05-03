package com.nano.naver_m.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nano.naver_m.exceptions.OrderDetailsNotFoundException;
import com.nano.naver_m.models.OrderDetails;
import com.nano.naver_m.repository.CartRepository;

@Service
public class CartService {

	@Autowired
	CartRepository cartRepository;
	public OrderDetails addToCart(OrderDetails order){
		int quantity = order.getQuantity();
		if(quantity > 1) {
			//if quantity is greater than one, find the existing order in repository. and do a PUT, not a POST.
			OrderDetails o = cartRepository.findByUserIdAndProductId(order.getUserId(), order.getProductId()).orElseThrow(()->new OrderDetailsNotFoundException(order.getId()));
			o.setQuantity(quantity);
			return cartRepository.save(o);
		}
		return cartRepository.save(order);
	}
}
