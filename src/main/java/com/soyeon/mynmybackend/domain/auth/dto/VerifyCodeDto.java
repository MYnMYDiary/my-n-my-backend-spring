package com.soyeon.mynmybackend.domain.auth.dto;

public record VerifyCodeDto(
      String email,
      String code
) {}
