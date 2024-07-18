package com.example.backend.exception;

public class DifferentPasswordException extends RuntimeException {

    public DifferentPasswordException() {
        super("Passwords don't match");
    }
}
