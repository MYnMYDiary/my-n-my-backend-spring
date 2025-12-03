package com.soyeon.mynmybackend.domain.auth.web;

import com.soyeon.mynmybackend.domain.auth.dto.VerifyCodeDto;
import com.soyeon.mynmybackend.domain.auth.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

      private final EmailService emailService;

      @PostMapping("/send-email")
      public ResponseEntity<String> sendEmail(@RequestParam String email) {
            emailService.sendAuthCode(email);
            return ResponseEntity.ok("success");
      }

      @PostMapping("/verify-email")
      public ResponseEntity<String> verifyCode(@RequestBody VerifyCodeDto req) {
            emailService.verifyCode(req.email(), req.code());
            return ResponseEntity.ok("success");
      }
}
