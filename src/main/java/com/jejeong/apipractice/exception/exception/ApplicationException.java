package com.jejeong.apipractice.exception.exception;

import com.jejeong.apipractice.exception.errorCode.CommonErrorCode;
import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {

    private CommonErrorCode errorCode;
    private String message;

    public ApplicationException(CommonErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = null;
    }

    public ApplicationException(CommonErrorCode errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    @Override
    public String getLocalizedMessage() {
        if (message == null) return errorCode.getMessage();
        return String.format("%s. %s", errorCode.getMessage(), message);
    }
}
