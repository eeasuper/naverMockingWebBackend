package com.nano.naver_m.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nano.naver_m.models.User;

public interface UserRepository extends JpaRepository<User, Long>{
	public User findByUsername(String username);
}
