package com.nano.naver_m.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nano.naver_m.models.OrderDetails;
import com.nano.naver_m.models.Product;
import com.nano.naver_m.models.User;


public interface CartRepository extends JpaRepository<OrderDetails, Long> {
	public List<OrderDetails> findByUserOrderByIdAsc(User user);
	public OrderDetails findByUserAndProduct(User user, Product product);
}
