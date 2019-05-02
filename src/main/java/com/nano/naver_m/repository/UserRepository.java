package com.nano.naver_m.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nano.naver_m.models.SiteUser;

public interface UserRepository extends JpaRepository<SiteUser, Long>{
	public Optional<SiteUser> findByUsername(String username);
	public boolean existsByUsername(String username);
}
