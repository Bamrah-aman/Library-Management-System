package com.GrabbingTheCode.bookmng.core.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class BookNotFoundException extends RuntimeException{

    private String code;
    private String message;
    private HttpStatus httpStatus;

    public BookNotFoundException(BookExceptionReason reason) {
        this.code = reason.getCode();
        this.message = reason.getMessage();
        this.httpStatus = reason.getHttpStatus();
    }
}
