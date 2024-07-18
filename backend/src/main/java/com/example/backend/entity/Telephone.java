package com.example.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
public class Telephone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    @Column(nullable = false)
    private String telephoneNumber;

    @NotBlank
    @Column(nullable = false)
    private String type;

    @ManyToOne
    @JoinColumn(name = "contact_id", nullable = false)
    private Contact contact;

}
