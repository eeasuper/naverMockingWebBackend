package com.nano.naver_m.assemblers;

import org.springframework.hateoas.ResourceSupport;

import com.nano.naver_m.models.User;

public class UserResource  extends ResourceSupport {
	public String JWTtoken;
	public User user;
	long userId;
	
	public UserResource(String jWTtoken, User user, long userId) {
		super();
		this.JWTtoken = jWTtoken;
		this.user = user;
		this.userId = userId;
	}
	
}
