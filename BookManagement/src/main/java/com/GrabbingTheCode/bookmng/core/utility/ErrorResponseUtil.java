package com.GrabbingTheCode.bookmng.core.utility;



import com.GrabbingTheCode.bookmng.core.dto.CustomErrorResponseDTO;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public final class ErrorResponseUtil {

    public ErrorResponseUtil() {
        System.out.println("Default Constructor");
    }

    public static CustomErrorResponseDTO build(final String code, final String message, final HttpStatus httpStatus,
                                               final String path) {
        return buildDetails(code, message, httpStatus, path);
    }

    private static CustomErrorResponseDTO buildDetails(String code, String message, HttpStatus httpStatus, String path) {
        return CustomErrorResponseDTO.builder()
                .code(code)
                .localDateTime(LocalDateTime.now())
                .path(path)
                .httpStatus(httpStatus)
                .message(message)
                .build();
    }
}
