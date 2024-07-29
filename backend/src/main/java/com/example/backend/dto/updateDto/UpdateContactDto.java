package com.example.backend.dto.updateDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * This class updates the Contact. When updating a contact, only their personal details (not Telephone, Email or Address) are updated.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateContactDto {

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Surname is mandatory")
    private String surname;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date birthday;

    @NotNull(message = "Contact emergency is mandatory")
    private Boolean contactEmergency;
}
