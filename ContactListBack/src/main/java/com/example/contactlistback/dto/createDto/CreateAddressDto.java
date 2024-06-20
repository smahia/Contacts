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
public class CreateAddressDto {

    @NotBlank(message = "Address is mandatory")
    private String address;

    @NotBlank(message = "Address type is mandatory")
    private String type;
}
