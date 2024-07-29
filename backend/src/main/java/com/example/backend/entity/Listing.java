package com.example.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
public class Listing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    /**
     * CascadeType is PERSIST and MERGE, so this way does not include REMOVE and so the removal
     * is not propagated through the ManyToMany relationship, so it does not delete all lists and contacts.
     */
    @OneToMany (mappedBy = "list",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<Contact> contactList = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
