package com.example.tasktrackerhttp.Exception;

public class UserVerificationException extends RuntimeException {
    public UserVerificationException (String message) {
        super(message);
    }
}
