package com.app_fitness.getaway_service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String jwtKey;

    @Value("${jwt.expiration}")
    private Long jwtExp;


    private Key convertKey(){
        byte[] keyBytes = jwtKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    public String generateToken(String userId, String email, Collection<? extends GrantedAuthority> authorities){
        Map<String, Object> claims = new HashMap<>();
        claims.put("authorities", authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));
        claims.put("sub", userId);
        claims.put("email", email);
        return createToken(claims);
    }

    private String createToken(Map<String, Object> claims){
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExp))
                .signWith(convertKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    public boolean isValid(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(convertKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }


    public Claims estraiAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(convertKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String estraiUserId(String token) {
        return estraiAllClaims(token).getSubject();
    }

    public String estraiEmail(String token) {
        return estraiAllClaims(token).get("email", String.class);
    }

    public List<String> estraiAuthorities(String token) {
        return estraiAllClaims(token).get("authorities", List.class);
    }

    public List<? extends GrantedAuthority> estraiListAuthorities(String token){
        return estraiAuthorities(token).stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    public Date estraiExpiration(String token){
        return  estraiAllClaims(token).getExpiration();
    }

    public boolean isTokenExpired(String token){
        return estraiExpiration(token).before(new Date());
    }


}
