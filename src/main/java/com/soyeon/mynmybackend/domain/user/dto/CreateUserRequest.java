package com.soyeon.mynmybackend.domain.user.dto;

public record CreateUserRequest(
      String email,
      String password,
      String name,
      String nickname
) {}
