package com.nano.naver_m.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nano.naver_m.models.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
//https://stackoverflow.com/questions/38040572/spring-boot-loading-initial-data
}
