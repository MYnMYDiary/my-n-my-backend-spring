package com.soyeon.mynmybackend.domain.user.entity;

import com.soyeon.mynmybackend.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity {

      @Id
      @GeneratedValue(strategy = GenerationType.UUID)
      @Column(updatable = false, nullable = false)
      private UUID id;

      @Column(unique = true)
      private String email;

      private String name;

      private String nickname;

      private String password;

      @Enumerated(EnumType.STRING)
      private UserRole role = UserRole.USER;

      private String profile_image;

      private LocalDate deleted_at;
}
