package com.nano.naver_m.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Product {
	private @Id @GeneratedValue @Column(nullable=false) Long id;
	private @Column(nullable=false) String productName;
	private @Column(nullable=false) String imagePathInFrontEnd;
	private @Column(nullable=false) int price;
	
	public Product() {
		
	}
	
	public Product(String productName, String imagePathInFrontEnd,int price) {
		super();
		this.productName = productName;
		this.imagePathInFrontEnd = imagePathInFrontEnd;
		this.price = price;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getImagePathInFrontEnd() {
		return imagePathInFrontEnd;
	}

	public void setImagePathInFrontEnd(String imagePathInFrontEnd) {
		this.imagePathInFrontEnd = imagePathInFrontEnd;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	
	
	
}
