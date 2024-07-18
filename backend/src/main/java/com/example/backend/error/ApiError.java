package com.example.backend.error;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
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
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class ApiError {

    @NonNull
    @Schema(description = "Http status",
            name = "status",
            example = "404 - Not Found")
    private HttpStatus status;

    @JsonFormat(shape = Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
    private LocalDateTime date = LocalDateTime.now();

    @NonNull
    private String message;

}
