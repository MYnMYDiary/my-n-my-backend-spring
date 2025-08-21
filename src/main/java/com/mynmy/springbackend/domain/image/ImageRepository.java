package com.mynmy.springbackend.domain.image;

import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Repository
public class ImageRepository {

    /** 공통 저장 메서드 */
    private File saveFile(String directory, String filename, byte[] content) throws IOException {
        Path dirPath = Paths.get(directory);
        Files.createDirectories(dirPath); // 디렉토리 없으면 생성

        Path filePath = dirPath.resolve(filename);
        Files.write(filePath, content);

        return filePath.toFile();
    }

    /** 이미지 조회 (공통) */
    public byte[] loadFile(String directory, String filename) throws IOException {
        Path dirPath = Paths.get(directory);
        Path filePath = dirPath.resolve(filename);

        if (!Files.exists(filePath)) {
            throw new IOException("파일이 존재하지 않습니다: " + filePath);
        }
        return Files.readAllBytes(filePath);
    }

    /** 임시 저장 */
    public File saveTemp(String filename, byte[] content) throws IOException {
        return saveFile("temp", filename, content);
    }

    /** 프로필 저장 */
    public File saveProfile(String filename, byte[] content) throws IOException {
        return saveFile("images/profile", filename, content);
    }



}
