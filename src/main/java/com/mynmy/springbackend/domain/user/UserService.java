package com.mynmy.springbackend.domain.user;

import com.mynmy.springbackend.domain.image.ImageService;
import com.mynmy.springbackend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final ImageService imageService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User register(String name, String email, String nickname, String password, String imageId) {

        if(userRepository.findByEmail(email).isPresent()){
            throw new IllegalArgumentException("이미 사용중인 이메일 입니다.");
        }

        String encodedPassword = passwordEncoder.encode(password);

        User user = User.builder()
                .name(name)
                .email(email)
                .nickname(nickname)
                .password(encodedPassword)
                .profileImage(imageId)
                .build();

        return userRepository.save(user);
    }

    public String updateProfileImage(Long userId, MultipartFile image) {
        try {
            // 1. 파일 스토리지에 저장
            String imageFile = imageService.saveProfileImage(image);

            // 2. DB 필드에 업데이트
            userRepository.updateProfileImage(userId, imageFile);

            // 프론트에 돌려줄 URL 반환
            return "images/profile" + imageFile;
        } catch (IOException e) {
            throw new RuntimeException("프로필 이미지 저장 실패", e);
        }
    }
}
