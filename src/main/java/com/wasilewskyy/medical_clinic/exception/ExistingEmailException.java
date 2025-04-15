package com.wasilewskyy.medical_clinic.exception;

import org.springframework.http.HttpStatus;

public class ExistingEmailException extends MedicalClinicException {

    public ExistingEmailException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
