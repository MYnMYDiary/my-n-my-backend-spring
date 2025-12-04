package com.soyeon.mynmybackend.domain.user.web;

import com.soyeon.mynmybackend.domain.user.dto.CreateUserRequest;
import com.soyeon.mynmybackend.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

      private final UserService userService;

      @PostMapping
      public ResponseEntity<String> createWithEmail(@RequestBody CreateUserRequest req) {
            userService.create(req.email(), req.password(), req.name(), req.nickname());
            return ResponseEntity.ok("회원가입 성공");
      }
}
