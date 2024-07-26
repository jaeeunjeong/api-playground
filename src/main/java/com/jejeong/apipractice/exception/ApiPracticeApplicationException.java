package com.jejeong.apipractice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiPracticeApplicationException extends RuntimeException {

  private ErrorCode errorCode;
  private String message;

  public ApiPracticeApplicationException(ErrorCode errorCode) {
    this.errorCode = errorCode;
    this.message = message;
  }

  @Override
  public String getMessage() {
    if (message == null) {
      return errorCode.getMessage();
    } else {
      return String.format("%s. %s", errorCode.getMessage(), message);
    }
  }
}
