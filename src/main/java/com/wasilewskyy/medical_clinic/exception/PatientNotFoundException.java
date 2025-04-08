package com.wasilewskyy.medical_clinic.exception;

public class PatientNotFoundException extends RuntimeException {

    public PatientNotFoundException(String email) {
        super("Patient with this email " + email + " not found.");
    }
}
