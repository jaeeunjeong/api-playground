package com.jejeong.apipractice.exception.errorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode{

    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "Invalid parameter included"),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "Resource not exists"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error"),
    DUPLICATED_USER_EMAIL(HttpStatus.CONFLICT, "Duplicated user email"),
    DUPLICATED_USER_NICKNAME(HttpStatus.CONFLICT, "Duplicated user nickname"),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, " Invalid password"),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
