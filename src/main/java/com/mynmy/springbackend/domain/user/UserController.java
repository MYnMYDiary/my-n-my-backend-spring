package com.mynmy.springbackend.domain.user;


import com.mynmy.springbackend.domain.user.Dto.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@Tag(name="User", description = "사용자")
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Operation(summary = "사용자 정보 조회")
    @PostMapping
    public ResponseEntity<UserResponse> getUser(@AuthenticationPrincipal String email) {
        User user = userService.findByEmail(email);
        return ResponseEntity.ok(UserResponse.from(user));
    }

    @PostMapping("/profile-image")
    public ResponseEntity<String> updateImage(
            @AuthenticationPrincipal String email,
            @RequestParam("image") MultipartFile image
    ) {
        String url = userService.updateProfileImage(email, image);
        return ResponseEntity.ok(url);
    }
}
