package com.GrabbingTheCode.bookmng.core.exception.successResponse;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class SuccessResponse {

    private HttpStatus status;
    private String message;

    public SuccessResponse(SuccessResponseReason reason) {
        this.status = reason.getStatus();
        this.message = reason.getMessage();
    }
}
