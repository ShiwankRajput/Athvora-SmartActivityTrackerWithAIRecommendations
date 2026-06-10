package com.shivnexEngineering.FitnessTrackerApplication.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String SECRET_KEY;
    private static final long EXPIRATION_TIME = 1000*60*60*24; 
    private SecretKey key;

    @PostConstruct
    public void init(){
        key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String generateToken(String userId, String role) {

        return Jwts.builder()
                .header()
                    .type("JWT")
                    .and()
                .subject(userId)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(
                        new Date(System.currentTimeMillis()
                                + EXPIRATION_TIME)
                )
                .signWith(key)
                .compact();
    }

    public Claims extractClaims(String token){

        return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .getPayload();

    }

    public String extractUserId(String token){
        return extractClaims(token).getSubject();
    }

    public String extractUserRole(String token){
        return extractClaims(token).get("role", String.class);
    }

    public Date extractExpiration(String token){
        return extractClaims(token).getExpiration();
    }

    public boolean isTokenExpired(String token){
        return extractExpiration(token)
            .before(new Date());
    }

    public boolean validateToken(String token){
        try{
            return !isTokenExpired(token);
        }
        catch(Exception e){
            return false;
        }
    }

}
