package com.wasilewskyy.medical_clinic.exception;

import org.springframework.http.HttpStatus;

public class PatientNotFoundException extends MedicalClinicException {

    public PatientNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
