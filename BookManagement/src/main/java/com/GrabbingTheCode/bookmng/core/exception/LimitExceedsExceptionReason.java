package com.GrabbingTheCode.bookmng.core.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum LimitExceedsExceptionReason {

    LIMIT_EXCEEDS_FOR_PAGE("Only five books can be added at a time", HttpStatus.BAD_REQUEST);

    private final String code = LimitExceedsException.class.getSimpleName();
    private final String message;
    private final HttpStatus httpStatus;

    LimitExceedsExceptionReason(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
