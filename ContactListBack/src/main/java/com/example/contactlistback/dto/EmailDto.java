package com.example.contactlistback.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailDto {

    @NotNull(message = "ID is mandatory")
    private int id;

    @Email(message = "Please provide a valid email address")
    @NotBlank(message = "Email is mandatory")
    private String email;

    @NotBlank(message = "Email type is mandatory")
    private String type;
}
