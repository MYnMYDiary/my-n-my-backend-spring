package com.mynmy.springbackend.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;
    private Key key;

    @PostConstruct
    public void init() {
        // secretKey를 기반으로 HMAC 키 생성 (서명용)
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    /**
     * JWT accessToken 생성 (20분 뒤 만료)
     * @param email 사용자 식별자
     * @return JWT 문자열
     */
    public String createAccessToken(String email) {

        long EXPIRATION_TIME = 1000 * 60 * 20; // 20분

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * RefreshToken 생성 (7일 뒤 만료)
     */
    public String createRefreshToken() {
        return UUID.randomUUID().toString(); // 그냥 고유 문자열
    }

    /**
     * 토큰에서 subject(email) 추출
     * @param token JWT 문자열
     * @return 사용자 이메일
     */
    public String getSubject(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * JWT 토큰 유효성 검증
     * @param token 검증할 JWT
     * @return 유효하면 true, 아니면 false
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            log.warn("JWT 검증 실패: {}", e.getMessage());
            return false;
        }
    }
}
