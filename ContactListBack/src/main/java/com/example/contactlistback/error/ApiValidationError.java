package com.example.contactlistback.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Error model to customise the response body of validation errors when a MethodArgumentNotValidException is thrown
 * The date is being set as the current date in the constructor, so there is no need to add it
 */
@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class ApiValidationError {

    @NonNull
    @Schema(description = "Http status",
            name = "status",
            example = "400 - Bad Request")
    private HttpStatus status;

    @JsonFormat(shape = Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
    private LocalDateTime date = LocalDateTime.now();

    @NonNull
    private List<String> errors;

}
