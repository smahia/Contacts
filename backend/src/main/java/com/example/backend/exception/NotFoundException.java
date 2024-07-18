package com.example.backend.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * Exception class for when an element (e.g. a Contact) cannot be found
 */
@Getter @Setter
public class NotFoundException extends RuntimeException {

    /**
     * @param errorMessage The error message to be displayed
     * @param id The id of the element, e.g. when searching for a contact
     */
    public NotFoundException(String errorMessage, int id) {
        super(errorMessage + ": " + id);
    }
}
