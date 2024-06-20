package com.example.contactlistback.dto.createDto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
