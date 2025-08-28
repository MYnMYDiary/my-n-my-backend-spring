package com.mynmy.springbackend.common.api;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.util.Map;
import java.util.UUID;

@RestControllerAdvice
public class ApiExceptionHandler {

    // Validation 실패
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);
        pd.setTitle("Validation failed");
        pd.setDetail("입력 값이 올바르지 않습니다.");
        pd.setType(URI.create("https://api.mynmy.com/errors/validation"));
        pd.setProperty("instance", req.getRequestURI());
        pd.setProperty("errors", ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> Map.of("field", fe.getField(), "reason", fe.getCode()))
                .toList());
        pd.setProperty("correlationId", UUID.randomUUID().toString());
        return pd;
    }

    // 리소스 없음
    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handleNotFound(ResourceNotFoundException ex, HttpServletRequest req) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        pd.setTitle("Not Found");
        pd.setType(URI.create("https://api.mynmy.com/errors/not-found"));
        pd.setProperty("instance", req.getRequestURI());
        pd.setProperty("correlationId", UUID.randomUUID().toString());
        return pd;
    }

    // 그 외 모든 런타임 에러
    @ExceptionHandler(RuntimeException.class)
    public ProblemDetail handleRuntimeException(RuntimeException ex, HttpServletRequest req) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        pd.setTitle("Internal Server Error");
        pd.setType(URI.create("https://api.mynmy.com/errors/internal"));
        pd.setProperty("instance", req.getRequestURI());
        pd.setProperty("correlationId", UUID.randomUUID().toString());
        return pd;
    }

}
