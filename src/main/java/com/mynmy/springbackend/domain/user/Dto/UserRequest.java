package com.mynmy.springbackend.domain.user.Dto;

import org.springframework.web.multipart.MultipartFile;

public class UserRequest {

    public record update (
            Long userId,
            String nickname,
            MultipartFile profileImage
    ) {}
}
