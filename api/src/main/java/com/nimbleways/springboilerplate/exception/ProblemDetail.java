package com.nimbleways.springboilerplate.exception;

public record ProblemDetail(
        int status,
        String title,
        String detail,
        String instance
) {
}
