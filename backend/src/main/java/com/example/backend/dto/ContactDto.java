package com.example.backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.*;

/**
 * These attributes must have the same name as the entity
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContactDto {

    private int id;

    private String name;

    private String surname;

    // This annotation makes that the front only receives the date (without time) in this specific format
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date birthday;

    private Boolean contactEmergency;

    private List<TelephoneDto> telephoneList = new ArrayList<>();

    private List<EmailDto> emailList = new ArrayList<>();

    private List<AddressDto> addressesList = new ArrayList<>();

    // If the name is not the same as in the Entity, it has to be converted manually even with Model Mapper
    private Integer listId;
}
