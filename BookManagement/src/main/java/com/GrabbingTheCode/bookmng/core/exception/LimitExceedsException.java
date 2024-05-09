package com.GrabbingTheCode.bookmng.core.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class LimitExceedsException extends RuntimeException {

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

    public LimitExceedsException(LimitExceedsExceptionReason reason) {
        this.code = reason.getCode();
        this.message = reason.getMessage();
        this.httpStatus = reason.getHttpStatus();
    }
}
