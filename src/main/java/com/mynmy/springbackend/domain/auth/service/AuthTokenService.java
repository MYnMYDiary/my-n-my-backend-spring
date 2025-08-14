package com.mynmy.springbackend.domain.auth.service;

import com.mynmy.springbackend.domain.auth.entity.RefreshToken;
import com.mynmy.springbackend.domain.auth.repository.RefreshTokenRepository;
import com.mynmy.springbackend.domain.user.User;
import com.mynmy.springbackend.domain.user.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthTokenService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public String issueRefreshToken(User user) {
        // 기존 토큰 제거
        refreshTokenRepository.deleteByUserId(user.getId());

        // 새로운 토큰 생성 및 저장
        String newToken = UUID.randomUUID().toString();
        RefreshToken entity = new RefreshToken(
                user.getId(),
                newToken,
                Instant.now().plus(7, ChronoUnit.DAYS)
        );
        refreshTokenRepository.save(entity);
        return newToken;
    }

    /**
     * 클라이언트에게 refreshToken을 HttpOnly 쿠키로 전달하는 메서드.
     *
     * <p>이 메서드는 클라이언트가 Authorization 헤더에 담아 보낸 accessToken을
     * Spring Security 필터가 디코딩하여 SecurityContext에 저장한 후,
     * {@link org.springframework.security.core.annotation.AuthenticationPrincipal}로 전달받은
     * 사용자 이메일을 기반으로 refreshToken을 생성하고 쿠키로 설정한다.
     *
     * @param email 클라이언트의 accessToken에서 추출된 사용자 이메일
     * @param response HttpServletResponse 객체. 쿠키를 추가하기 위해 사용됨
     */
    public void setRefreshTokenToCookie(String email, HttpServletResponse response) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("해당 이메일의 사용자가 존재하지 않습니다."));

        String refreshToken = issueRefreshToken(user);

        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(7 * 24 * 60 * 60);
        response.addCookie(cookie);

        log.info("refreshToken={}", refreshToken);
    }
}
