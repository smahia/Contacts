package com.example.contactlistback.error;

import java.time.LocalDateTime;

import lombok.*;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

/**
 * Error model to customise the response body of an error
 * Status and message are mandatory
 * The date is being set as the current date in the constructor, so there is no need to add it
 */
@Setter
@Getter
@RequiredArgsConstructor
@NoArgsConstructor
public class ApiError {

    @NonNull
    private HttpStatus status;

    @JsonFormat(shape = Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
    private LocalDateTime date = LocalDateTime.now();

    @NonNull
    private String message;

}
