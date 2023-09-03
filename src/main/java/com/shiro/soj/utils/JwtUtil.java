package com.shiro.soj.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

public class JwtUtil {

    // 这是你的密钥，用于签发和验证 JWT，密钥长度至少为 256 位
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // JWT 过期时间（单位：毫秒）
    private static final long JWT_EXPIRATION = 7200000; // 2h

    // 生成 JWT
    public static String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setSubject("userId")
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
                .signWith(SECRET_KEY)
                .compact();
    }

    // 从 JWT 中解析出过期时间
    public static Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // 从 JWT 中解析出特定声明
    public static <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private static Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // 验证 JWT 是否有效
    public static boolean validateToken(String token) {
        try {
            final Date expiration = extractExpiration(token);
            return !expiration.before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
