package com.mynmy.springbackend.domain.image;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    // 여러 장 업로드 (최대 10장)
    @PostMapping("/upload")
    public ResponseEntity<List<String>> uploadImages(@RequestParam("image") List<MultipartFile> images) {
        if (images.size() > 10) {
            throw new IllegalArgumentException("최대 10개 파일만 업로드할 수 있습니다.");
        }
        List<String> savedFiles = imageService.saveTempImages(images);
        return ResponseEntity.ok(savedFiles);
    }
}
