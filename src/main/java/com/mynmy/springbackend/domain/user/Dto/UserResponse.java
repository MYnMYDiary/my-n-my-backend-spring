package com.mynmy.springbackend.domain.user.Dto;

import com.mynmy.springbackend.domain.user.User;
import com.mynmy.springbackend.domain.user.UserRole;
import lombok.Builder;

@Builder
public record UserResponse(
        String name,
        String email,
        UserRole role,
        String nickname,
        String createdAt,
        String updatedAt,
        String provider,
        String providerId,
        String profileImage
) {
    public static UserResponse from(User user){
        return UserResponse.builder()
            .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .nickname(user.getNickname())
                .profileImage(user.getProfileImage())
                .provider(user.getProvider())
                .providerId(user.getProviderId())
                .createdAt(user.getCreatedAtFormatted())
                .updatedAt(user.getUpdatedAtFormatted())
            .build();
    }
}
