package com.mynmy.springbackend.global;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

/**
 * REST API 전역 예외 처리 핸들러
 * 모든 RuntimeException을 JSON 형태로 클라이언트에 응답
 */
@RestControllerAdvice
public class GlobalRestExceptionAdvice {

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.status(401).body(Map.of(
                "error", e.getMessage()
        ));
    }
}
