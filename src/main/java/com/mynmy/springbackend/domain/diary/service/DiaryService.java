package com.mynmy.springbackend.domain.diary.service;

import com.mynmy.springbackend.domain.diary.entity.Diary;
import com.mynmy.springbackend.domain.diary.DiaryRepository;
import com.mynmy.springbackend.domain.user.UserRepository;
import com.mynmy.springbackend.domain.diary.dto.request.CreateDiaryRequest;
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
    public Diary create(CreateDiaryRequest request) {

        userRepository.findById(request.userId())
                .orElseThrow(() -> new RuntimeException("해당 아이디의 사용자가 존재하지 않습니다."));

        Diary diary = Diary.builder()
                .userId(request.userId())
                .categoryId(request.categoryId())
                .year(request.year())
                .month(request.month())
                .title(request.title())
                .content(request.content())
                .image(request.image())
                .build();

        return diaryRepository.save(diary);
    }
}
