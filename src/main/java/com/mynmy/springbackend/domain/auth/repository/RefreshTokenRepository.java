package com.mynmy.springbackend.domain.auth.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.TimeUnit;



@Repository
@RequiredArgsConstructor
public class RefreshTokenRepository {

    private static final String PREFIX = "refresh_token:";
    private static final long REFRESH_TOKEN_TTL = 60 * 60 * 24 * 7; // 7일
    private final RedisTemplate<String, String> redisTemplate;

    public void save(String email, String refreshToken) {
        String key = PREFIX + email;
        String value = refreshToken;
        redisTemplate.opsForValue().set(key, value, REFRESH_TOKEN_TTL, TimeUnit.SECONDS);
    }

    public Optional<String> findByEmail(String email) {
        String key = PREFIX + email;
        return Optional.ofNullable(redisTemplate.opsForValue().get(key));
    }

    public void delete(String email) {
        String key = PREFIX + email;
        redisTemplate.delete(key);
    }
}
