package com.mynmy.springbackend.domain.user;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/{userId}")
    public ResponseEntity<String> updateImage(
            @PathVariable("userId") Long userId,
            @RequestParam("image") MultipartFile image
    ) {
        String url = userService.updateProfileImage(userId, image);
        return ResponseEntity.ok(url);
    }

}
