package com.example.backend.dto.createDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * These attributes must have the same name as the entity
 * This class allows to create a new Contact (POST method)
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateContactDto {

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Surname is mandatory")
    private String surname;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date birthday;

    @NotNull(message = "Contact emergency is mandatory")
    private Boolean contactEmergency;

    /**
     * The annotation valid means the system will check if the items inside the list are
     * valid
     */
    private List<@Valid CreateTelephoneDto> telephoneList = new ArrayList<>();

    private List<@Valid CreateEmailDto> emailList = new ArrayList<>();

    private List<@Valid CreateAddressDto> addressesList = new ArrayList<>();
}
