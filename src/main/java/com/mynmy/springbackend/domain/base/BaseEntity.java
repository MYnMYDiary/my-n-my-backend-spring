package com.mynmy.springbackend.domain.base;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Getter
@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private Instant updatedAt;

    // 생성 시각 포맷팅
    public String getCreatedAtFormatted() {
        return formatTimestamp(createdAt);
    }

    // 업데이트 시각 포맷팅
    public String getUpdatedAtFormatted() {
        return formatTimestamp(updatedAt);
    }

    // 날짜(시간)포맷팅 로직
    public String formatTimestamp(Instant timestamp) {
        LocalDateTime dateTime = LocalDateTime.ofInstant(timestamp, ZoneId.systemDefault());
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
