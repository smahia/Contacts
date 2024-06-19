package com.example.contactlistback.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TelephoneDto {

    @NotNull(message = "ID is mandatory")
    private int id;

    @NotBlank(message = "Telephone number is mandatory")
    private String telephoneNumber;

    @NotBlank(message = "Telephone type is mandatory")
    private String type;
}
