package com.example.contactlistback.handler;

import com.example.contactlistback.error.ApiError;
import com.example.contactlistback.error.ApiValidationError;
import com.example.contactlistback.exception.DifferentPasswordException;
import com.example.contactlistback.exception.NotFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

/**
 * Class that implements the logic when an exception is thrown.
 */
@RestControllerAdvice
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler {

    /**
     * Method that handles a MethodArgumentNotValidException thrown when a validation fails
     * Springs ResponseEntityExceptionHandler has a method handleException annotated with
     * MethodArgumentNotValidException.class, so the handleMethodArgumentNotValid must be overridden and not annotated
     * https://stackoverflow.com/questions/38282298/ambiguous-exceptionhandler-method-mapped-for-class-org-springframework-web-bin
     * @param ex MethodArgumentNotValidException Exception to be thrown
     * when validation on an argument annotated with @Valid fails
     * @param headers HttpHeaders
     * @param status HttpStatusCode
     * @param request WebRequest
     * @return ResponseEntity<Object>
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {

        List<String> errorList = ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        ApiValidationError apiValidationError = new ApiValidationError(HttpStatus.BAD_REQUEST, errorList);
        return new ResponseEntity<>(apiValidationError, status);
    }

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

    /**
     * Method that handles a DifferentPasswordException thrown when passwords don't match
     * @param ex DifferentPasswordException
     * @return ResponseEntity<ApiError>
     */
    @ExceptionHandler(DifferentPasswordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiError> handleDifferentPasswordException(DifferentPasswordException ex) {

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }
}
