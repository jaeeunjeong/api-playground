package com.jejeong.apipractice.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenUtils {

    public static Boolean validate(String token, String userEmail, String key) {
        String userEmailByToken = getUserEmail(token, key);
        return userEmailByToken.equals(userEmail) && !isTokenExpired(token, key);
    }

    public static String getUserEmail(String token, String key) {
        return extractAllClaims(token, key)
            .get("userEmail", String.class);
    }

    private static Claims extractAllClaims(String token, String key) {
        return Jwts.parserBuilder()
            .setSigningKey(getSigningKey(key))
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    private static Key getSigningKey(String key) {
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public static Boolean isTokenExpired(String token, String key) {
        Date expiration = extractAllClaims(token, key).getExpiration();
        return expiration.before(new Date());
    }

    public static String generateAccessToken(String userEmail, String key, long expiredTimeMs) {
        return doGenerateToken(userEmail, key, expiredTimeMs);
    }

    public static String generateRefreshToken(String userEmail, String key, long expiredTimeMs) {
        return doGenerateToken(userEmail, key, expiredTimeMs);
    }

    private static String doGenerateToken(String userEmail, String key, long expireTime) {
        Claims claims = Jwts.claims();
        claims.put("userEmail", userEmail);

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + expireTime))
            .signWith(getSigningKey(key), SignatureAlgorithm.HS256)
            .compact();
    }
}
