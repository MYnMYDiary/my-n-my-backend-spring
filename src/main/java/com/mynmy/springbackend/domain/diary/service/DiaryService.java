package com.mynmy.springbackend.domain.diary.service;

import com.mynmy.springbackend.domain.diary.dto.Request;
import com.mynmy.springbackend.domain.diary.entity.Diary;
import com.mynmy.springbackend.domain.diary.repository.DiaryRepository;
import com.mynmy.springbackend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiaryService {

    private final UserRepository userRepository;
    private final DiaryRepository diaryRepository;

    /**
     * 다이어리 생성
     *
     */
    public Diary create(Request.create req) {

        userRepository.findById(req.userId())
                .orElseThrow(() -> new RuntimeException("해당 아이디의 사용자가 존재하지 않습니다."));

        Diary diary = Diary.builder()
                .userId(req.userId())
                .categoryId(req.categoryId())
                .year(req.year())
                .month(req.month())
                .title(req.title())
                .content(req.content())
                .image(req.image())
                .build();

        return diaryRepository.save(diary);
    }
}
