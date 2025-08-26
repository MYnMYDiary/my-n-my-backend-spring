package com.mynmy.springbackend.domain.user;


import com.mynmy.springbackend.domain.user.Dto.UserRequest;
import com.mynmy.springbackend.domain.user.Dto.UserResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Tag(name="User", description = "사용자")
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/{userId}")
    public ResponseEntity<String> updateImage(
            @PathVariable("userId") Long userId,
            @RequestParam("image") MultipartFile image
    ) {
        String url = userService.updateProfileImage(userId, image);
        return ResponseEntity.ok(url);
    }

//    @GetMapping()
//    public ResponseEntity<UserResponse> getUser(@AuthenticationPrincipal String email) {
//        return
//    }


}
