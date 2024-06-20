package com.example.contactlistback.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Annotation and Class have the same name, so the full import must be written here
     */
    @NotBlank
    //@jakarta.validation.constraints.Email
    @Column(nullable = false)
    private String email;

    @NotBlank
    @Column(nullable = false)
    private String type;

    /*
    * Many emails for one contact
     */
    @ManyToOne
    @JoinColumn(name = "contact_id", nullable = false)
    private Contact contact;

}
