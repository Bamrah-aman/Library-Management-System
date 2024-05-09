package com.GrabbingTheCode.bookmng.core.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
public class BookAlreadyExistsException extends RuntimeException{

    private String code;
    private String message;
    private HttpStatus httpStatus;

    public BookAlreadyExistsException(BookAlreadyExistsReason reason) {
        this.code = reason.getCode();
        this.message = reason.getMessage();
        this.httpStatus = reason.getHttpStatus();
    }
}
