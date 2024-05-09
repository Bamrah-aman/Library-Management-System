package com.GrabbingTheCode.bookmng.core.exception.successResponse;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum SuccessResponseReason {

    BOOKS_ADDED_SUCCESSFULLY("Books successfully added", HttpStatus.CREATED),
    AUTHOR_UPDATED_SUCCESSFULLY("Author name has been updated", HttpStatus.OK),
    BOOKS_UPDATED_SUCCESSFULLY("Book name has been updated", HttpStatus.OK),
    DELETE_THE_AUTHOR("Author has been deleted", HttpStatus.NO_CONTENT),
    DELETE_THE_BOOK("Book has been deleted", HttpStatus.NO_CONTENT);

    private final HttpStatus status;
    private final String message;

     SuccessResponseReason(String message, HttpStatus  status) {
        this.status = status;
        this.message = message;
    }
}
