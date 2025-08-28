package com.mynmy.springbackend.domain.auth.web;

import com.mynmy.springbackend.common.api.ApiResponse;
import com.mynmy.springbackend.domain.auth.service.AuthService;
import com.mynmy.springbackend.domain.image.ImageService;
import com.mynmy.springbackend.domain.user.Dto.UserRequest;
import com.mynmy.springbackend.domain.user.Dto.UserResponse;
import com.mynmy.springbackend.domain.user.User;
import com.mynmy.springbackend.domain.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Slf4j
@RestController
@Tag(name="Auth")
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final ImageService imageService;

    @Operation(summary = "회원가입")
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

    @Operation(summary = "로그인")
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Map<String, String>>> login(@RequestBody UserRequest.Login req, HttpServletResponse response) {
        String accessToken = authService.login(req.email(), req.password(), response);
        return ResponseEntity.ok(ApiResponse.success(
                Map.of("accessToken", accessToken),
                "로그인 성공"
        ));
    }

    @Operation(summary = "accessToken 발급")
    @PostMapping("/token/access")
    public ResponseEntity<String> setAccessToken(@AuthenticationPrincipal String email) {
        String accessToken = authService.setAccessToken(email);
        return ResponseEntity.ok(accessToken);
    }

    @Operation(summary = "refreshToken 발급")
    @PostMapping("/set-refresh")
    public ResponseEntity<?> setRefreshToken(@AuthenticationPrincipal String email, HttpServletResponse response) {
        authService.setRefreshToken(email, response);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "로그아웃")
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@AuthenticationPrincipal String email, HttpServletResponse response) {
         authService.logout(email, response);
         return ResponseEntity.ok("로그아웃 성공");
    }
}
