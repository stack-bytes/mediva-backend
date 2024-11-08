package com.stackbytes.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class JwtUtil {
    @Autowired
    private GetProperties getProperties;
    public String getToken(String jwtHeader) {
        String key = getProperties.getProperties("jwt.secret");
        Map<String, Object> claims = new HashMap<>();
        try {
            return Jwts.builder()
                    .setClaims(claims)
                    .setSubject(jwtHeader.toString())
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .signWith(SignatureAlgorithm.HS256, key)
                    .compact();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
