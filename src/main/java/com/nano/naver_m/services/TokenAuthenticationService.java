package com.nano.naver_m.services;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.nano.naver_m.models.SiteUser;
import com.nano.naver_m.repository.UserRepository;

import io.jsonwebtoken.Claims;

//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenAuthenticationService {
	
    static final long EXPIRATIONTIME = 864_000_000; // 10 days
    
    static final String SECRET = "Fjapsijf0183lFlso0slfs";
     
    static final String TOKEN_PREFIX = "Bearer";
     
    static final String HEADER_STRING = "Authorization";
    
    public SiteUser addToken(String username, String password, SiteUser user) {
    	Authentication authentication = new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
		authentication.isAuthenticated();
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
    	Claims userClaim = Jwts.claims();
    	userClaim.put("usr_id", user.getId());
    	userClaim.put("sub", username);
        String JWT = Jwts.builder()
        		.setClaims(userClaim)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                .signWith(SignatureAlgorithm.HS512, SECRET).compact();
		user.setToken(JWT);
		return user;
    }
    
    public static void addAuthentication(HttpServletResponse res, String username) {
    						
        String JWT = Jwts.builder().setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                .signWith(SignatureAlgorithm.HS512, SECRET).compact();
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
    }
    
    
    public static Authentication getAuthentication(HttpServletRequest request) {
    	//This function is for authentication when user is logged in and asks for an api.
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            String username = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody()
                    .getSubject();
            
            return username != null ? new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList()) : null;
        }
        return null;
    }
     
}
