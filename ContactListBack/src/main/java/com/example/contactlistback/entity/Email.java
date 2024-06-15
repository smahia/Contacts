package com.example.contactlistback.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String type;

    /*
    * Many emails for one contact
     */
    @ManyToOne
    @JoinColumn(name = "contact_id", nullable = false)
    private Contact contact;

}
