package com.soyeon.mynmybackend.domain.user.web;

import com.soyeon.mynmybackend.domain.auth.service.EmailService;
import com.soyeon.mynmybackend.domain.user.dto.CreateUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

      private final EmailService emailService;

      @PostMapping
      public ResponseEntity<String> createWithEmail(@RequestBody CreateUserRequest req) {
            return ResponseEntity.ok("success");
      }
}
