package com.jejeong.apipractice.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

  INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "Invalid token"),
  USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not founded"),
  POST_NOT_FOUND(HttpStatus.NOT_FOUND, "Post not founded"),
  INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "Invalid password"),
  DUPLICATED_USER_NAME(HttpStatus.CONFLICT, "Duplicated user name"),
  DUPLICATED_EMAIL(HttpStatus.CONFLICT, "Duplicated email"),
  ALREADY_LIKED_POST(HttpStatus.CONFLICT, "User already like the post"),
  INVALID_PERMISSION(HttpStatus.UNAUTHORIZED, "User has invalid permission"),
  DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Database error occurs"),
  NOTIFICATION_CONNECT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "connect to notication")
  ;

  private final HttpStatus status;
  private final String message;
}
