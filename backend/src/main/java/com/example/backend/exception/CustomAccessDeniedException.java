package com.example.backend.exception;

public class CustomAccessDeniedException extends RuntimeException {

    public CustomAccessDeniedException() {
        super("You do not have sufficient permission to perform this operation.");
    }
}
