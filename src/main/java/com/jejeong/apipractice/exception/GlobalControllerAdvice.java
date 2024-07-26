package com.jejeong.apipractice.exception;

import static com.jejeong.apipractice.exception.ErrorCode.DATABASE_ERROR;

import com.jejeong.apipractice.controller.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {

  @ExceptionHandler(ApiPracticeApplicationException.class)
  public ResponseEntity<?> errorHandler(ApiPracticeApplicationException e){
    return ResponseEntity.status(e.getErrorCode().getStatus())
        .body(Response.error(e.getErrorCode().name()));
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<?> databaseErrorHandler(IllegalArgumentException e){
    log.error("Error occurs {}", e.toString());
    return ResponseEntity.status(DATABASE_ERROR.getStatus())
        .body(Response.error(DATABASE_ERROR.name()));
  }
}
