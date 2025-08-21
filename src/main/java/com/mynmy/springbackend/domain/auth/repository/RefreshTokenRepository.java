package com.mynmy.springbackend.domain.auth.repository;

import com.mynmy.springbackend.domain.auth.entity.RefreshToken;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByToken(String token);

    @Transactional
    @Modifying
    void deleteByUserId(Long userId);
}
