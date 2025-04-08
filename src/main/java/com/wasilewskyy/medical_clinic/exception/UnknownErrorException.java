package com.wasilewskyy.medical_clinic.exception;

public class UnknownErrorException extends RuntimeException {

    public UnknownErrorException(String message) {
        super("Unknown error: " + message);
    }
}
