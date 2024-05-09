package com.GrabbingTheCode.bookmng.core.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum BookExceptionReason {

    BOOK_NOT_EXIST_CANNOT_UPDATE_AUTHOR("Cannot update Author because Book not Found", HttpStatus.BAD_REQUEST),
    BOOK_OR_AUTHOR_NOT_MATCH_CHECK_AGAIN("Book and Author did not match", HttpStatus.BAD_REQUEST),
    INCORRECT_OLD_BOOK_NAME("Old book name is incorrect or Blank", HttpStatus.BAD_REQUEST),
    BOOK_NOT_FOUND_FOR_THE_GIVEN_AUTHOR("Book not exist for the Author", HttpStatus.NOT_FOUND),
    BOOKS_NOT_FOUND("BookS not found for given Author/bookName", HttpStatus.NOT_FOUND);

    private final String code = BookExceptionReason.class.getSimpleName();
    private final String message;
    private final HttpStatus httpStatus;

    BookExceptionReason(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
