package com._anhtai.aistudymentor.utils;

import com._anhtai.aistudymentor.dto.reponse.Token;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTUtils {
    private final long EXPIRATION_TIME = 1000L * 60 * 60 * 24 * 7;
    @Value("${SECRET_KEY}")
    private String SECRET_KEY;
    private  SecretKey key;
    public Token generateToken(String username) {
        Token token = new Token();

        String accessToken = Jwts.builder()
                .setSubject(username)
                .signWith(key,SignatureAlgorithm.HS256)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .compact();
        return token.builder()
                .accessToken(accessToken)
                .build();
    }
    public String extractUsername(String token){
        return extractToken(token).getSubject();
    }
    public Claims extractToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public boolean validateToken(String email, UserDetails userDetails, String token) {
        return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));

    }
    public boolean isTokenExpired(String token) {
        return extractToken(token).getExpiration().before(new Date());
    }
    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }
}
