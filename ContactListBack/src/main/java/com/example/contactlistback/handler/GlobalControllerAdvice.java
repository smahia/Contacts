package com.example.contactlistback.handler;


import com.example.contactlistback.error.ApiError;
import com.example.contactlistback.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Class that implements the logic when an exception is thrown.
 */
@RestControllerAdvice
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler {

    /**
     * When a NotFoundException is thrown, this method handles the exception and returns a Response Entity with a
     * 404 error code (in the request header) and the ApiError body,
     * which contains the status, the message (which is the message from the NotFoundException class)
     * and the local date
     * @param ex The NotFoundException
     * @return ResponseEntity<ApiError>
     */
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiError> handleNotFoundException(NotFoundException ex) {

        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }
}
