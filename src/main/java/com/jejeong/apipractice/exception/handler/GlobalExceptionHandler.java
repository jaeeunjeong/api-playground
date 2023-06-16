package com.jejeong.apipractice.exception.handler;

import com.jejeong.apipractice.exception.errorCode.CommonErrorCode;
import com.jejeong.apipractice.exception.errorCode.ErrorCode;
import com.jejeong.apipractice.exception.exception.ApplicationException;
import com.jejeong.apipractice.exception.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<?> errorHandler(ApplicationException e) {
        log.error("Error occur {}", e.toString());
        return handleExceptionInternal(e.getErrorCode());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> illegalArgumentHandler(ApplicationException e) {
        log.error("IllegalArgument Error occur {}", e.toString());
        return handleExceptionInternal(CommonErrorCode.INVALID_PARAMETER, e.getMessage());
    }


    private static ResponseEntity<?> handleExceptionInternal(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
            .body(makeErrorResponse(errorCode));
    }

    private static ErrorResponse makeErrorResponse(ErrorCode e) {
        return ErrorResponse.builder()
            .code(e.name())
            .message(e.getMessage())
            .build();
    }


    private static ResponseEntity<?> handleExceptionInternal(ErrorCode errorCode, String message) {
        return ResponseEntity.status(errorCode.getHttpStatus())
            .body(makeErrorResponse(errorCode, message));
    }

    private static ErrorResponse makeErrorResponse(ErrorCode e, String message) {
        return ErrorResponse.builder()
            .code(e.name())
            .message(message)
            .build();
    }
}
