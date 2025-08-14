package com.mynmy.springbackend.security.oauth;

import com.mynmy.springbackend.domain.user.User;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Getter
public class CustomOAuth2User implements OAuth2User {
    private final User user;
    private final Map<String, Object> attributes;

    public CustomOAuth2User(User user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<SimpleGrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getName() {
        return user.getName();
    }

    public String getEmail() {
        return user.getEmail();
    }
}
