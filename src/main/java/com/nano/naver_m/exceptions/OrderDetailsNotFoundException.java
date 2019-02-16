package com.nano.naver_m.exceptions;

import com.nano.naver_m.models.Product;
import com.nano.naver_m.models.User;

public class OrderDetailsNotFoundException extends RuntimeException{
	public OrderDetailsNotFoundException(Long id) {
		super("Could not find order with ID: " + id);
	}
}
