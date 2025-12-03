package com.soyeon.mynmybackend.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
      INTERNAL_SERVER_ERROR("서버 내부 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
      INVALID_REQUEST("잘못된 요청입니다.", HttpStatus.BAD_REQUEST),
      NOT_FOUND("요청한 리소스를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
      UNAUTHORIZED("인증되지 않은 사용자입니다.", HttpStatus.UNAUTHORIZED),
      FORBIDDEN("접근 권한이 없습니다.", HttpStatus.FORBIDDEN)
      ;

      private final String message;
      private final HttpStatus httpStatus;
}
