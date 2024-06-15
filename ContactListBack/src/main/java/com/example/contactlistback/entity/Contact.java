package com.example.contactlistback.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String surname;

    private Date birthday;

    private boolean contactEmergency;

    @OneToMany(
            mappedBy = "contact",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Telephone> telephoneList = new ArrayList<>();

    @OneToMany(
            mappedBy = "contact",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Email> emailList = new ArrayList<>();

    @OneToMany(
            mappedBy = "contact",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Address> addressesList = new ArrayList<>();

    @ManyToMany(mappedBy = "contactList")
    private List<Listing> lists;

}
