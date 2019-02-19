package com.nano.naver_m.services;

import java.security.Principal;
import java.util.Collections;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.nano.naver_m.models.User;
import com.nano.naver_m.repository.UserRepository;

import io.jsonwebtoken.Claims;

//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokenAuthenticationService {
	
    static final long EXPIRATIONTIME = 864_000_000; // 10 days
    
    static final String SECRET = "ThisIsASecret";
     
    static final String TOKEN_PREFIX = "Bearer";
     
    static final String HEADER_STRING = "Authorization";
    
    public static void addAuthentication(HttpServletResponse res, String username) {
//    public static String addAuthentication(User user) {
//    	if(authResult == null) {
//    		return;
//    	}
    	//not sure if it's getName or toString here.
//    	User user = repository.findByUsername(authResult.getName());
//    	Claims userClaim = Jwts.claims();
//    	userClaim.put("username", user.getUsername());
//    	userClaim.put("id", user.getId());
//    	userClaim.put("email", user.getEmail());
    						
        String JWT = Jwts.builder().setSubject(username)
//        		.setClaims(userClaim)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                .signWith(SignatureAlgorithm.HS512, SECRET).compact();
        //compact(): Actually builds the JWT and serializes it to a compact, URL-safe string according to the JWT
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
//        return JWT;
    }
    
    
    public static Authentication getAuthentication(HttpServletRequest request) {
    	//This function is for authentication when user is logged in and asks for an api.
    	System.out.println("tokenAuthenticationService getAuthentication()");
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            String username = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody()
                    .getSubject();
            
            return username != null ? new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList()) : null;
        }
        return null;
    }
     
}
