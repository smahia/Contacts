package com.example.contactlistback.entity;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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

    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotBlank
    @Column(nullable = false)
    private String surname;

    /**
     * This annotation saves only the date in the database
     */
    @Temporal(TemporalType.DATE)
    private Date birthday;

    /**
     * NotNull constraint only works when it's the Object (Boolean) and not the type (boolean)
     * https://stackoverflow.com/questions/53769165/java-hibernate-no-validator-could-be-found-for-boolean
     */
    @NotNull
    @Column(columnDefinition = "boolean default false", nullable = false)
    private Boolean contactEmergency;

    @NotEmpty
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
