package com.wasilewskyy.medical_clinic.exception;

import org.springframework.http.HttpStatus;

public class MedicalClinicException extends RuntimeException {

    private final HttpStatus status;

    public MedicalClinicException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
    public HttpStatus getStatus() {
        return status;
    }
}
