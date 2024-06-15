package com.example.contactlistback.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Telephone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String telephoneNumber;

    private String type;

    @ManyToOne
    @JoinColumn(name = "contact_id", nullable = false)
    private Contact contact;

}
