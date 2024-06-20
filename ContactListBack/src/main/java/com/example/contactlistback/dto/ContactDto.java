package com.example.contactlistback.dto;

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

    private int id;

    private String name;

    private String surname;

    private Date birthday;

    private Boolean contactEmergency;

    private List<TelephoneDto> telephoneList = new ArrayList<>();

    private List<EmailDto> emailList = new ArrayList<>();

    private List<AddressDto> addressesList = new ArrayList<>();
}
