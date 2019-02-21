package com.nano.naver_m.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nano.naver_m.models.User;

public interface UserRepository extends JpaRepository<User, Long>{
	public Optional<User> findByUsername(String username);
}
