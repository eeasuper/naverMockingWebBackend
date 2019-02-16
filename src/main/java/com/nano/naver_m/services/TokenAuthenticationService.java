package com.nano.naver_m.services;

import java.util.Collections;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nano.naver_m.models.User;

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
 
    public static String addAuthentication(User user) {
    	Claims userClaim = Jwts.claims();
    	userClaim.put("username", user.getUsername());
    	userClaim.put("id", user.getId());
    	userClaim.put("email", user.getEmail());
    						
        String JWT = Jwts.builder().setSubject(user.getUsername())
        		.setClaims(userClaim)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                .signWith(SignatureAlgorithm.HS512, SECRET).compact();
                //add scope?
        //compact(): Actually builds the JWT and serializes it to a compact, URL-safe string according to the JWT 
        String token = HEADER_STRING + " " + TOKEN_PREFIX + " " + JWT;
        return token;
    }
    
    
//    public static Authentication getAuthentication(HttpServletRequest request) {
//        String token = request.getHeader(HEADER_STRING);
//        if (token != null) {
//            // parse the token.
//            String username = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody()
//                    .getSubject();
//            
////            if(userId == request.getParameter(userId))
//            
//            return username != null ? new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList()) : null;
//        }
//        return null;
//    }
     
}
