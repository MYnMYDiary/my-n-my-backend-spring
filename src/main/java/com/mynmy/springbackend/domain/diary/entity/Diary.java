package com.mynmy.springbackend.domain.diary.entity;

import com.mynmy.springbackend.domain.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "\"diary\"")
public class Diary extends BaseEntity {

    private Long userId;
    private Long categoryId;
    private String year;
    private String month;
    private String title;
    private String content;
    private String image;
    private int likeCount;
    private int commentCount;

    @Builder
    public Diary(Long userId, Long categoryId, String year, String month,
                 String title, String content, String image,
                 int likeCount, int commentCount) {
        this.userId = userId;
        this.categoryId = categoryId;
        this.year = year;
        this.month = month;
        this.title = title;
        this.content = content;
        this.image = image;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
    }
}
