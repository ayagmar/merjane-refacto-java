package com.nimbleways.springboilerplate.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotSupportedException.class)
    public ResponseEntity<ProblemDetail> handleProductNotSupportedException(ProductNotSupportedException ex, WebRequest request) {
        ProblemDetail problemDetail = new ProblemDetail(
                HttpStatus.BAD_REQUEST.value(),
                "Product not supported",
                ex.getMessage(),
                request.getDescription(false)
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleOrderNotFoundException(OrderNotFoundException ex, WebRequest request) {
        ProblemDetail problemDetail = new ProblemDetail(
                HttpStatus.NOT_FOUND.value(),
                "Order is not found",
                ex.getMessage(),
                request.getDescription(false)
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleGenericException(Exception ex, WebRequest request) {
        ProblemDetail problemDetail = new ProblemDetail(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Unexpected error",
                ex.getMessage(),
                request.getDescription(false)
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problemDetail);
    }
}
