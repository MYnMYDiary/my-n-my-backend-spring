package com.mynmy.springbackend.domain.user.Dto;

import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

public class UserRequest {

    public record Create(
            @NotBlank(message = "이름을 입력해주세요")
            String name,
            @NotBlank(message = "이메일을 입력해주세요")
            String email,
            @NotBlank(message = "비밀번호를 입력해주세요")
            String password,
            String nickname
    ) {}

    public record update (
            Long userId,
            String nickname,
            MultipartFile profileImage
    ) {}

    public record Login(
        @NotBlank(message = "이메일을 입력해주세요")
        String email,
        @NotBlank(message = "비밀번호를 입력해주세요")
        String password
    ) {}
}
