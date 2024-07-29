package com.jejeong.apipractice.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenUtils {

  @Value("${jwt.token.secret-key.access}")
  private String accessSecretKey;

  @Value("${jwt.token.secret-key.refresh}")
  private String refreshSecretKey;

  @Value("${jwt.token.expired-time-ms.access}")
  private Long accessExpiredTimeMs;

  @Value("${jwt.token.expired-time-ms.refresh}")
  private Long refreshExpiredTimeMs;

  public String generateAccessToken(String username) {
    return doGenerateToken(username, accessSecretKey, accessExpiredTimeMs);
  }

  public String generateRefreshToken(String username) {
    return doGenerateToken(username, accessSecretKey, refreshExpiredTimeMs);
  }


  private String doGenerateToken(String username, String key, long expiredTimeMs) {
    Claims claims = Jwts.claims();
    claims.put("username", username);

    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + expiredTimeMs))
        .signWith(SignatureAlgorithm.HS256, getSigningKey(key))
        .compact();
  }


  public Boolean validate(UserDetails userDetails, String token, String key) {
    String username = getUsername(token, key);
    return username.equals(userDetails.getUsername()) && !isTokenExpired(token, key);
  }

  public String getUsername(String token, String key) {
    return extractAllClaims(token, key).get("username", String.class);
  }

  public Claims extractAllClaims(String token, String key) {
    return Jwts.parserBuilder()
        .setSigningKey(getSigningKey(key))
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  private Key getSigningKey(String secretKey) {
    byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  public Boolean isTokenExpired(String token, String key) {
    Date expiration = extractAllClaims(token, key).getExpiration();
    return expiration.before(new Date());
  }

//  public static long getRemainMilliSeconds(String token, String key) {
//    Date expiration = extractAllClaims(token, key).getExpiration();
//    return expiration.getTime() - new Date().getTime();
//  }
}
