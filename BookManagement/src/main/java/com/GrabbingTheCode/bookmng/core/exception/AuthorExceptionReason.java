package com.GrabbingTheCode.bookmng.core.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum AuthorExceptionReason {

    AUTHOR_NOT_EXIST_CHECK_AGAIN_CANNOT_UPDATE_BOOK("Cannot Update BookName because Author not found", HttpStatus.BAD_REQUEST),
    OLD_AUTHOR_NOT_FOUND("Old Author name is Incorrect or Blank", HttpStatus.BAD_REQUEST),
    AUTHOR_NOT_FOUND("Author not found unable to delete", HttpStatus.NOT_FOUND);

    private final String code = AuthorExceptionReason.class.getSimpleName();
    private final String message;
    private final HttpStatus httpStatus;

    AuthorExceptionReason(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
