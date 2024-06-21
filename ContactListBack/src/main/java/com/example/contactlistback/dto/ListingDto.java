package com.example.contactlistback.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ListingDto {

    private int id;

    private String name;

    private List<ContactDto> contactList = new ArrayList<>();

    private UserDto user;
}
