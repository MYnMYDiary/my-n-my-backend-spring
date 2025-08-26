package com.mynmy.springbackend.domain.auth.web;

import com.mynmy.springbackend.domain.image.ImageService;
import com.mynmy.springbackend.domain.user.Dto.UserRequest;
import com.mynmy.springbackend.domain.user.Dto.UserResponse;
import com.mynmy.springbackend.domain.user.User;
import com.mynmy.springbackend.domain.user.UserService;
import com.mynmy.springbackend.domain.user.repository.UserRepository;
import com.mynmy.springbackend.domain.auth.service.AuthService;
import com.mynmy.springbackend.domain.auth.service.AuthTokenService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@Tag(name="Auth")
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AuthTokenService authTokenService;
    private final UserService userService;
    private final ImageService imageService;

    @PostMapping(value = "/signup", consumes = "multipart/form-data")
    public ResponseEntity<UserResponse> create(
            @Valid @RequestPart("user") UserRequest.Create req,
            @RequestPart(value = "profileImage", required = false ) MultipartFile image){

        String imageId = null;

        if(image != null && !image.isEmpty()){
            try{
                imageId = imageService.saveProfileImage(image);
            } catch(Exception e){
                log.error("프로필 이미지 저장 실패 " + e);
                throw new IllegalArgumentException("프로필 이미지 저장 실패");
            }
        }

        User user = userService.register(req.name(), req.email(), req.nickname(), req. password(), imageId);

        return ResponseEntity.ok(UserResponse.from(user));
    }

    @PostMapping("/set-refresh")
    public ResponseEntity<?> setRefreshToken(@AuthenticationPrincipal String email, HttpServletResponse response) {
        authTokenService.setRefreshTokenToCookie(email, response);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
        return authService.refreshToken(request);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        return authService.logout(request, response);
    }
}
