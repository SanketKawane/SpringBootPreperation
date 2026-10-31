package com.Project.SpringBootPreperation.service;

import com.Project.SpringBootPreperation.entity.UserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Service
public class JWTService {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.access-expiration}")
    private long accessExpiration;

    @Value("${jwt.refresh-expiration}")
    private long refreshExpiration;

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public String generateAccessToken(UserDetails userDetails)
    {

        return Jwts.builder()
                .subject(userDetails.getEmail())
                .claims(Map.of("role", userDetails.getRole()))
                .issuedAt(new Date())
                .expiration(
                        new Date(System.currentTimeMillis()
                                + accessExpiration)
                )
                .signWith(getSigningKey())
                .compact();
    }
    public String generateRefreshToken(UserDetails userDetails){
        return Jwts.builder()
                .subject(userDetails.getEmail())
                .claim("tokenType", "refresh")
                .issuedAt(new Date())
                .expiration(
                        new Date(System.currentTimeMillis()
                                + refreshExpiration)
                )
                .signWith(getSigningKey())
                .compact();
    }
    public String extractEmail(String token){
        return extractClaim(token).getSubject();
    }
    public Claims extractClaim(String token) {

        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    public boolean isAccessTokenValid(String token, UserDetails user) {

        Claims claims = extractClaim(token);

        return claims.getSubject().equals(user.getEmail())
                && claims.getExpiration().after(new Date())
                && !"refresh".equals(claims.get("tokenType", String.class));
    }

    public long getAccessExpiration() {
        return accessExpiration;
    }
}