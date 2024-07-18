package com.example.backend.handler;

import com.example.backend.error.ApiError;
import com.example.backend.error.ApiValidationError;
import com.example.backend.exception.CustomAccessDeniedException;
import com.example.backend.exception.DifferentPasswordException;
import com.example.backend.exception.NotFoundException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
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
     *
     * @param ex      MethodArgumentNotValidException Exception to be thrown
     *                when validation on an argument annotated with @Valid fails
     * @param headers HttpHeaders
     * @param status  HttpStatusCode
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
     *
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
     *
     * @param ex DifferentPasswordException
     * @return ResponseEntity<ApiError>
     */
    @ExceptionHandler(DifferentPasswordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiError> handleDifferentPasswordException(DifferentPasswordException ex) {

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    /**
     * Handle CustomAccessDeniedException thrown when a user tries
     * to perform an action that they are not allowed to perform
     *
     * @param ex CustomAccessDeniedException
     * @return ResponseEntity<ApiError>
     */
    @ExceptionHandler(CustomAccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ApiError> handleAccessDeniedException(CustomAccessDeniedException ex) {

        ApiError apiError = new ApiError(HttpStatus.FORBIDDEN, ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(apiError);
    }

    /**
     * Handler for when a security exception occurs
     *
     * @param exception Represent the security exceptions, such as bad credentials, access denied
     *                  or expired JWT token
     * @return ProblemDetail
     */
    @ExceptionHandler(Exception.class)
    public ProblemDetail handleSecurityException(Exception exception) {

        ProblemDetail errorDetail = null;

        if (exception instanceof BadCredentialsException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), exception.getMessage());
            errorDetail.setProperty("description", "The username or password is incorrect");

            return errorDetail;
        }

        if (exception instanceof AccountStatusException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty("description", "The account is locked");
        }

        if (exception instanceof AccessDeniedException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty("description", "You are not authorized to access this resource");
        }

        if (exception instanceof SignatureException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty("description", "The JWT signature is invalid");
        }

        if (exception instanceof ExpiredJwtException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty("description", "The JWT token has expired");
        }

        if (errorDetail == null) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(500), exception.getMessage());
            errorDetail.setProperty("description", "Unknown internal server error.");
        }

        return errorDetail;
    }
}
