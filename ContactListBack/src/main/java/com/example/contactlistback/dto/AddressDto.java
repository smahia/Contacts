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
public class AddressDto {

    @NotNull(message = "ID is mandatory")
    private int id;

    @NotBlank(message = "Address is mandatory")
    private String address;

    @NotBlank(message = "Address type is mandatory")
    private String type;
}
