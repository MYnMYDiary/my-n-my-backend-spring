package com.mynmy.springbackend.domain.auth.service;

import com.mynmy.springbackend.domain.auth.entity.RefreshToken;
import org.springframework.data.repository.Repository;

interface RefreshTokenRepository extends Repository<RefreshToken, Long> {
}
