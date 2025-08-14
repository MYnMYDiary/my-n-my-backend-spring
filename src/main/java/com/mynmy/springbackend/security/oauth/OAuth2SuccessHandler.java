package com.mynmy.springbackend.security.oauth;

import com.mynmy.springbackend.domain.user.User;
import com.mynmy.springbackend.security.jwt.JwtTokenProvider;
import com.mynmy.springbackend.domain.auth.service.AuthTokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthTokenService authTokenService;

    @Transactional
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        // 1. 인증된 사용자 정보 가져오기
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        User user = oAuth2User.getUser();

        // 2. JWT 발급
        String accessToken = jwtTokenProvider.createAccessToken(oAuth2User.getUser().getEmail());
        log.info("accessToken: {}", accessToken);

        // 3. AccessToken을 url로 클라이언트에게 전달
        response.sendRedirect("http://localhost:3000/auth/google?token=" + accessToken);

    }
}
