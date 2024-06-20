package com.example.contactlistback.dto.createDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * This class allows to create a new Email (PUT and POST methods)
 */
@Data
public class CreateEmailDto {

    @Email(message = "Please provide a valid email address")
    @NotBlank(message = "Email is mandatory")
    private String email;

    @NotBlank(message = "Email type is mandatory")
    private String type;
}
