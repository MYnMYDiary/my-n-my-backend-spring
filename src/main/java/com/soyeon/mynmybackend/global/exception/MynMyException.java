package com.soyeon.mynmybackend.global.exception;

import lombok.Getter;

@Getter
public class MynMyException extends RuntimeException {

      private final ErrorCode errorCode;

      public MynMyException(ErrorCode errorCode) {
            super(errorCode.getMessage());
            this.errorCode = errorCode;
      }

      public MynMyException(ErrorCode errorCode, String message) {
            super(message);
            this.errorCode = errorCode;
      }
}
