package com.example.backend.entity;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
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
//    @Temporal(TemporalType.DATE)
    private Date birthday;

    /**
     * NotNull constraint only works when it's the Object (Boolean) and not the type (boolean)
     * https://stackoverflow.com/questions/53769165/java-hibernate-no-validator-could-be-found-for-boolean
     */
    @NotNull
    @Column(columnDefinition = "boolean default false", nullable = false)
    private Boolean contactEmergency;

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
    private List<EmailAddress> emailList = new ArrayList<>();

    @OneToMany(
            mappedBy = "contact",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Address> addressesList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "contactList", nullable = false)
    private Listing list;

}
