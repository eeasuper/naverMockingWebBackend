package com.nano.naver_m.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nano.naver_m.models.OrderDetails;
import com.nano.naver_m.models.Product;
import com.nano.naver_m.models.SiteUser;


public interface CartRepository extends JpaRepository<OrderDetails, Long> {
	public List<OrderDetails> findByUserIdOrderByIdAsc(Long userId);
//	public OrderDetails findByUserAndProduct(User user, Product product);
	public Long deleteByUserIdAndProductId(Long userId, Long productId); 
}
