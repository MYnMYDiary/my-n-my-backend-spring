package com.mynmy.springbackend.domain.image;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;

@Slf4j
@Component
public class ImageCleanupTask {

    private static final String FOLDER = "temp";
    private static final long EXPIRE_TIME = 24 * 60 * 60 * 1000; // 24시간 (ms)

    // 매일 새벽 3시 정각 실행
    @Scheduled(cron = "0 0 3 * * ?")
    public void cleanup() {
        File folder = new File(FOLDER);

        if (!folder.exists()) {
            log.info("temp 폴더가 존재하지 않아 정리할 파일이 없습니다.");
            return;
        }

        File[] files = folder.listFiles();
        if (files == null || files.length == 0) {
            log.info("temp 폴더가 비어있습니다.");
            return;
        }

        long now = System.currentTimeMillis();
        int deleted = 0;

        for (File file : files) {
            if (file.isFile()) {
                long age = now - file.lastModified();
                if (age > EXPIRE_TIME) {
                    if (file.delete()) {
                        deleted++;
                        log.debug("삭제됨: {}", file.getName());
                    } else {
                        log.warn("삭제 실패: {}", file.getAbsolutePath());
                    }
                }
            }
        }

        log.info("temp 폴더 정리 완료. 24시간 이상 된 파일 삭제 개수: {}", deleted);
    }
}
