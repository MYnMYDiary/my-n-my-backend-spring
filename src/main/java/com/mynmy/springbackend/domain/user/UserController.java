package com.mynmy.springbackend.domain.user;


import com.mynmy.springbackend.security.oauth.CustomOAuth2User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    @GetMapping("/login")
    public ResponseEntity<String> getMyInfo(@AuthenticationPrincipal CustomOAuth2User user) {
        return ResponseEntity.ok("로그인된 유저 이메일: " + user.getEmail());
    }
}
