package com.example.contactlistback.dto.createDto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class allows to create a new Telephone (PUT and POST methods)
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateTelephoneDto {

    @NotBlank(message = "Telephone number is mandatory")
    private String telephoneNumber;

    @NotBlank(message = "Telephone type is mandatory")
    private String type;
}
