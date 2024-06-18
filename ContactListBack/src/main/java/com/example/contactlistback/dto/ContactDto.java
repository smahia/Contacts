package com.example.contactlistback.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContactDto {

    @NotNull(message = "ID is mandatory")
    private int id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Surname is mandatory")
    private String surname;

    // TODO: fix date
    private Date birthday;

    @NotNull(message = "Contact emergency is mandatory")
    private Boolean contactEmergency;

    @NotEmpty(message = "At least one telephone must be included")
    private List<@Valid TelephoneDto> telephoneList = new ArrayList<>();
}
