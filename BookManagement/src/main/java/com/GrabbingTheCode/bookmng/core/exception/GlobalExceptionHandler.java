package com.GrabbingTheCode.bookmng.core.exception;


import com.GrabbingTheCode.bookmng.core.dto.CustomErrorResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.ServletWebRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static com.GrabbingTheCode.bookmng.core.utility.ErrorResponseUtil.build;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends RuntimeException{

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public CustomErrorResponseDTO handleUncaughtExceptions(final Exception ex, final ServletWebRequest servletWebRequest) {
        log.error(ex.getMessage(), servletWebRequest);
        return CustomErrorResponseDTO.builder()
                .localDateTime(LocalDateTime.now())
                .path(servletWebRequest.getRequest().getRequestURI())
                .code("Unhandled Exception Caught")
                .message(ex.getMessage())
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public CustomErrorResponseDTO handleMethodArgumentsNotValid(MethodArgumentNotValidException ex,
                                                                HttpServletRequest servletRequest) {
        log.error(Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage(), servletRequest);
        List<String> listOfErrors = ex.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable :: getDefaultMessage)
                .toList();
        return CustomErrorResponseDTO.builder()
                .localDateTime(LocalDateTime.now())
                .path(servletRequest.getServletPath())
                .code("Validation Failed")
                .message(String.join(", ", listOfErrors))
                .httpStatus(HttpStatus.BAD_REQUEST)
                .build();
    }

    @ExceptionHandler(value = LimitExceedsException.class)
    public ResponseEntity<Object> limitExceedException(final LimitExceedsException ex, final ServletWebRequest webRequest) {
        final CustomErrorResponseDTO customErrorResponseDTO = build(ex.getCode(), ex.getMessage(), ex.getHttpStatus()
        , webRequest.getRequest().getRequestURI());
        return ResponseEntity.status(ex.getHttpStatus()).body(customErrorResponseDTO);
    }

    @ExceptionHandler(value = BookAlreadyExistsException.class)
    public ResponseEntity<Object> bookExistsException(final BookAlreadyExistsException ex, ServletWebRequest webRequest) {
        log.error(ex.getMessage(), webRequest);
        final CustomErrorResponseDTO customErrorResponseDTO = build(ex.getCode(), ex.getMessage(), ex.getHttpStatus(),
                webRequest.getRequest().getRequestURI());
        return ResponseEntity.status(ex.getHttpStatus()).body(customErrorResponseDTO);
    }

    @ExceptionHandler(value = AuthorNotFoundException.class)
    public ResponseEntity<Object> authorNotFoundException(final AuthorNotFoundException ex, final ServletWebRequest webRequest) {
        log.error(ex.getMessage(), webRequest);
        final CustomErrorResponseDTO customErrorResponseDTO = build(ex.getCode(), ex.getMessage(), ex.getHttpStatus(),
                webRequest.getRequest().getRequestURI());
        return ResponseEntity.status(ex.getHttpStatus()).body(customErrorResponseDTO);
    }

    @ExceptionHandler(value = BookNotFoundException.class)
    public ResponseEntity<Object> bookNotFoundException(final BookNotFoundException ex, final ServletWebRequest webRequest) {
        log.error(ex.getMessage(), webRequest);
        final CustomErrorResponseDTO customErrorResponseDTO = build(ex.getCode(), ex.getMessage(), ex.getHttpStatus()
                , webRequest.getRequest().getRequestURI());
        return ResponseEntity.status(ex.getHttpStatus()).body(customErrorResponseDTO);
    }
}

