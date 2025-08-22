package com.mynmy.springbackend.domain.image;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    /** 프로필 이미지 저장 */
    public String saveProfileImage(MultipartFile image) throws IOException {
        validateFile(image);
        String uniqueName = createUniqueFilename(image.getOriginalFilename());
        imageRepository.saveProfile(uniqueName, image.getBytes());
        return uniqueName;
    }


    /** 이미지 임시 저장 */
    public List<String> saveTempImages(List<MultipartFile> images) {
        return images.stream()
                .map(image -> {
                    // 1. 이미지 형식 검사
                    validateFile(image);
                    // 2. 이미지 이름 생성
                    String uniqueName = createUniqueFilename(image.getOriginalFilename());

                    try {
                        imageRepository.saveTemp(uniqueName, image.getBytes());
                        return uniqueName; // 파일명 반환
                    } catch (IOException e) {
                        throw new RuntimeException("이미지 저장 실패: " + image.getOriginalFilename(), e);
                    }
                })
                .toList();
    }

    /** 이미지 형식 검사*/
    public void validateFile(MultipartFile file) {
        String contentType = file.getContentType();
        if (contentType == null ||
                (!contentType.equals("image/jpeg") && !contentType.equals("image/png"))) {
            throw new IllegalArgumentException("jpg/png 형식만 업로드 가능합니다.");
        }
    }

    /** 파일 이름 생성 */
    public String createUniqueFilename(String originalFilename) {
        if (originalFilename == null || originalFilename.isBlank()) {
            // 파일명이 없으면 확장자 없이 UUID만 반환
            return UUID.randomUUID().toString();
        }

        int dotIndex = originalFilename.lastIndexOf(".");
        String ext = (dotIndex != -1) ? originalFilename.substring(dotIndex) : "";

        return UUID.randomUUID().toString() + ext;
    }
}
