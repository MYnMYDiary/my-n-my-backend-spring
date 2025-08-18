package com.mynmy.springbackend.domain.image;

import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Repository
public class ImageRepository {

    private final Path tempPath = Paths.get("temp");

    public File saveTemp(String filename, byte[] content) throws IOException {
        if (!tempPath.toFile().exists()) tempPath.toFile().mkdirs();
        Path filePath = tempPath.resolve(filename);
        Files.write(filePath, content);
        return filePath.toFile();
    }

    public File findInFinal(String filename) {
        return tempPath.resolve(filename).toFile();
    }
}
