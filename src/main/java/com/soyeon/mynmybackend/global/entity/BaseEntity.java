package com.soyeon.mynmybackend.global.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
      @CreatedDate
      @Column(columnDefinition = "timestamp with time zone", updatable = false, nullable = false)
      private LocalDateTime createdAt;

      @LastModifiedDate
      @Column(columnDefinition = "timestamp with time zone")
      private LocalDateTime updatedAt;
}
