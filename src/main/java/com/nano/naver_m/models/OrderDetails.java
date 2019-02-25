package com.nano.naver_m.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
//import javax.persistence.OneToOne;

@Entity
public class OrderDetails {
	private @Id @GeneratedValue Long id;
//	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
//	@JoinColumn(name="USER_ID")
//	private User user;
//	@ManyToOne(fetch=FetchType.LAZY,cascade=CascadeType.ALL)
//	@JoinColumn(name="PRODUCT_ID")
//	private Product product;
	private Long userId;
	private Long productId;
	private int quantity; 
	
	public OrderDetails() {
		
	}
	
	public OrderDetails(Long userId, Long productId, int quantity) {
		super();
		this.userId = userId;
		this.productId = productId;
		this.quantity = quantity;
	}

//	public OrderDetails(Long id, User user, Product product, int quantity) {
//		super();
//		this.id = id;
//		this.user = user;
//		this.product = product;
//		this.quantity = quantity;
//	}
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

//	public OrderDetails(User user, Product product, int quantity) {
//		super();
//		this.user = user;
//		this.product = product;
//		this.quantity = quantity;
//	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
//	public User getUser() {
//		return user;
//	}
//	public void setUser(User user) {
//		this.user = user;
//	}
//	
//	public Product getProduct() {
//		return product;
//	}
//	public void setProduct(Product product) {
//		this.product = product;
//	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
}
