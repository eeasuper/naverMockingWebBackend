package com.nano.naver_m.assemblers;

import org.springframework.hateoas.ResourceSupport;

import com.nano.naver_m.models.SiteUser;

public class UserResource  extends ResourceSupport {
	public String JWTtoken;
	public SiteUser user;
	long userId;
	
	public UserResource(String jWTtoken, SiteUser user, long userId) {
		super();
		this.JWTtoken = jWTtoken;
		this.user = user;
		this.userId = userId;
	}
	
}
