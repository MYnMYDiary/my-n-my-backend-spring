package com.mynmy.springbackend.domain.diary.web;

import com.mynmy.springbackend.domain.diary.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/diary")
public class DiaryController {

    private final DiaryService diaryService;

    @PostMapping("/create")
    public ResponseEntity<?> create() {


        return ResponseEntity.ok().build();
    }
}
