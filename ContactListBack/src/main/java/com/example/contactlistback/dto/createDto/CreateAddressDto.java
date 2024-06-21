package com.example.contactlistback.dto.createDto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class allows to create and edit a new Address (PUT and POST methods)
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateAddressDto {

    @NotBlank(message = "Address is mandatory")
    private String address;

    @NotBlank(message = "Address type is mandatory")
    private String type;
}
