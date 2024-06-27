package com.example.contactlistback.exception;

public class DifferentPasswordException extends RuntimeException {

    public DifferentPasswordException() {
        super("Passwords don't match");
    }
}
