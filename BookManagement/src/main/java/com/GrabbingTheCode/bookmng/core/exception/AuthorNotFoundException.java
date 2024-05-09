package com.GrabbingTheCode.bookmng.core.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class AuthorNotFoundException extends RuntimeException{

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

    public AuthorNotFoundException(AuthorExceptionReason reason) {
        code = reason.getCode();;
        message = reason.getMessage();
        httpStatus = reason.getHttpStatus();
    }

}
