package com.mynmy.springbackend.domain.auth.service;

import com.mynmy.springbackend.domain.auth.repository.RefreshTokenRepository;
import com.mynmy.springbackend.domain.user.User;
import com.mynmy.springbackend.domain.user.repository.UserRepository;
import com.mynmy.springbackend.security.jwt.JwtTokenProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public String login(String email, String password, HttpServletResponse response) {

        // 사용자 조회
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자 입니다."));

        // 비밀번호 검증
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // refreshToken 발급 한 다음 redis에 저장
        String refreshToken = jwtTokenProvider.createRefreshToken();
        refreshTokenRepository.save(user.getEmail(), refreshToken);

        // 쿠키 생성
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(7 * 24 * 60 * 60);

        response.addCookie(cookie); // 응답으로 쿠키 보내기

        //accessToken 리턴
        return jwtTokenProvider.createAccessToken(user.getEmail());
    }

    public void logout(String email, HttpServletResponse response) {

        // 1. 토큰 제거
        refreshTokenRepository.delete(email);

        // 2. 쿠키 제거
        Cookie cookie = new Cookie("refreshToken", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");

        // 3. 응답으로 쿠기 전달
        response.addCookie(cookie);
    }

    public String setAccessToken(String email) {

        // 1. 사용자 검증
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        // 1. 리프레시 토큰 검증
        refreshTokenRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("토큰이 만료됐거나 유효하지 않습니다."));

        // 2. 새로운 accessToken 발급
        return jwtTokenProvider.createAccessToken(user.getEmail());
    }

    public void setRefreshToken(String email, HttpServletResponse response) {
        // 1. 사용자 검증
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        // 2. 기존 토큰이 존재하는지 확인 후 존재한다면 삭제
        if (refreshTokenRepository.findByEmail(user.getEmail()).isPresent()) {
            refreshTokenRepository.delete(user.getEmail());
        }

        String refreshToken = jwtTokenProvider.createRefreshToken();
        refreshTokenRepository.save(email, refreshToken);

        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(7 * 24 * 60 * 60);
        response.addCookie(cookie);
    }
}
