package com.GrabbingTheCode.bookmng.core.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Builder
public record CustomErrorResponseDTO(
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-dd HH:mm:ss")
        LocalDateTime localDateTime,
        String message,
        HttpStatus httpStatus,
        String code,
        String path
) {
}
