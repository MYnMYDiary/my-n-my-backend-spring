package com.mynmy.springbackend.domain.user;

import com.mynmy.springbackend.domain.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "\"user\"")
public class User extends BaseEntity {

    @Column(nullable = false)
    private String provider; // 소셜 로그인 ex. google

    @Column(nullable = false)
    private String providerId; // 구글 userId

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = true)
    private String nickname;

    @Column(nullable = true)
    private String profileImage;

    @Builder
    public User(String provider, String providerId, String name, String email, String nickname, String profileImage) {
        this.provider = provider;
        this.providerId = providerId;
        this.name = name;
        this.email = email;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.role = UserRole.USER;
    }
}
