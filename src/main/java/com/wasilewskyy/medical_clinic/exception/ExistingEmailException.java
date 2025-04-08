package com.wasilewskyy.medical_clinic.exception;

public class ExistingEmailException extends RuntimeException {

    public ExistingEmailException(String message) {
        super("Patient with this email address already exist." + message);
    }
}
