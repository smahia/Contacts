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

/**
 * These attributes must have the same name as the entity
 */
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

    private Date birthday;

    @NotNull(message = "Contact emergency is mandatory")
    private Boolean contactEmergency;

    /**
     * The annotation valid means the system will check if the items inside the list are
     * valid
     */
    @NotEmpty(message = "At least one telephone must be included")
    private List<@Valid TelephoneDto> telephoneList = new ArrayList<>();

    private List<@Valid EmailDto> emailList = new ArrayList<>();

    private List<@Valid AddressDto> addressesList = new ArrayList<>();
}
