package com.wasilewskyy.medical_clinic.exception;

import org.springframework.http.HttpStatus;

public class ErrorMessage extends RuntimeException {
    private final String message;
    private final HttpStatus status;

    public ErrorMessage(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
