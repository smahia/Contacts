package com.example.backend.dto.updateDto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class UpdatePasswordDto {

    @NotBlank(message = "Your current password is mandatory")
    private String oldPassword;

    @NotBlank(message = "Password is mandatory")
    private String newPassword;

    @NotBlank(message = "Password is mandatory")
    private String confirmPassword;
}
