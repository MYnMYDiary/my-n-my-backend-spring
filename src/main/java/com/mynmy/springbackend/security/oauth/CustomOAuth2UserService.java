package com.mynmy.springbackend.security.oauth;

import com.mynmy.springbackend.domain.user.User;
import com.mynmy.springbackend.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

/**
 * 구글 로그인 성공 후 사용자 정보 받아서 → DB(User 테이블)에 저장하거나 꺼내오는 역할
 **/
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) {
        OAuth2User oAuth2User = super.loadUser(request);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        // 구글이면 "sub", "email", "name" 이런 정보가 있음
        String provider = request.getClientRegistration().getRegistrationId(); // google
        String providerId = (String) attributes.get("sub");                   // 고유 ID
        String name = (String) attributes.get("name");
        String email = (String) attributes.get("email");

        Optional<User> userOpt = userRepository.findByProviderAndProviderId(provider, providerId);

        User user = userOpt.orElseGet(() ->
                userRepository.save(User.builder()
                        .provider(provider)
                        .providerId(providerId)
                        .name(name)
                        .email(email)
                        .nickname(null)
                        .profileImage(null)
                        .build())
        );


        // OAuth2User를 직접 리턴할 수 없으므로 CustomOAuth2User 객체로 감싸야 함
        return new CustomOAuth2User(user, attributes);
    }
}
