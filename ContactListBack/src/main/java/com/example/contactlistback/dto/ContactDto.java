package com.example.contactlistback.dto;

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

    private int id;

    private String name;

    private String surname;

    private Date birthday;

    private boolean contactEmergency;

    private List<TelephoneDto> telephoneList = new ArrayList<>();
}
