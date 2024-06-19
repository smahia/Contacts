package com.example.contactlistback.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailDto {

    @NotNull(message = "ID is mandatory")
    private int id;

    @NotBlank(message = "Email is mandatory")
    private String email;

    @NotBlank(message = "Email type is mandatory")
    private String type;
}
