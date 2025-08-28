package com.mynmy.springbackend.domain.user;

import com.mynmy.springbackend.domain.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@Entity
@SuperBuilder(toBuilder = true)   // ✅ 여기!
@NoArgsConstructor
@Table(name = "\"user\"")
public class User extends BaseEntity {

    @Column(nullable = true)
    private String provider; // 소셜 로그인 ex. google

    @Column(nullable = true)
    private String providerId; // 구글 userId

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private UserRole role =  UserRole.USER;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = true)
    private String nickname;

    @Column(nullable = true)
    private String password;

    @Column(nullable = true)
    private String profileImage;

    public void updateOAuthInfo(String provider, String providerId) {
        this.provider = provider;
        this.providerId = providerId;
    }
}
