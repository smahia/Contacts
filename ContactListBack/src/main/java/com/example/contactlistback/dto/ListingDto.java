package com.example.contactlistback.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ListingDto {

    private int id;

    private String name;

    // TODO: Return only the contactIds
    private Set<ContactDto> contactList = new HashSet<>();

    private UserDto user;
}
