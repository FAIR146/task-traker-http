package com.example.tasktrackerhttp.exception;

public class UserVerificationException extends RuntimeException {
    public UserVerificationException (String message) {
        super(message);
    }
}
