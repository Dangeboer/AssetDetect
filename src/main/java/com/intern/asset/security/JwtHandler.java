package com.intern.asset.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtHandler {
    final Key signingKey; // 用于签名 JWT 的密钥。这个密钥用来确保 JWT 的有效性和安全性

    // 注入应用程序配置文件中的密钥
    public JwtHandler(@Value("${assertDetect.jwt.secret-key}") String secretKey) {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        signingKey = Keys.hmacShaKeyFor(bytes);
    }

    // 用于解析 JWT 并获取用户名
    public String parsedUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // 用于生成一个 JWT，并将用户名（username）作为 subject 存入其中
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }
}
