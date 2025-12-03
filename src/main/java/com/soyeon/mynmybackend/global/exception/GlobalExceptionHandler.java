package com.soyeon.mynmybackend.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

      @ExceptionHandler(Exception.class)
      public ResponseEntity<ProblemDetail> handleException(Exception e) {
            log.error("예상치 못한 오류 발생: {}", e.getMessage(), e);
            ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());

            pd.setTitle("Internal Server Error");
            pd.setType(URI.create("https://mynmy.com/problems/internal-server-error"));
            pd.setDetail(e.getMessage());
            pd.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

            return new ResponseEntity<>(pd, HttpStatus.INTERNAL_SERVER_ERROR);
      }

      @ExceptionHandler(MynMyException.class)
      public ResponseEntity<ProblemDetail> handleMynMyException(MynMyException e) {
            ErrorCode errorCode = e.getErrorCode();
            log.error("MynMyException 발생: {} - {}", errorCode.getHttpStatus(), e.getMessage(), e);

            ProblemDetail pd = ProblemDetail.forStatusAndDetail(errorCode.getHttpStatus(), e.getMessage());
            pd.setTitle(errorCode.name());
            pd.setType(URI.create("https://mynmy.com/problems/" + errorCode.name().toLowerCase()));
            pd.setDetail(e.getMessage());
            pd.setStatus(errorCode.getHttpStatus().value());

            return new ResponseEntity<>(pd, errorCode.getHttpStatus());
      }
}
