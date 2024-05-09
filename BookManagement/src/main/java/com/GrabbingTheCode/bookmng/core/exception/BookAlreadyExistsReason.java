package com.GrabbingTheCode.bookmng.core.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum BookAlreadyExistsReason {

    BOOK_ALREADY_EXISTS("Book with the same name already Exits", HttpStatus.BAD_REQUEST);

    private final String code = BookAlreadyExistsException.class.getSimpleName();
    private final String message;
    private final HttpStatus httpStatus;

    BookAlreadyExistsReason(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
