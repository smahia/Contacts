package com.example.contactlistback.repository;

import com.example.contactlistback.entity.Contact;
import com.example.contactlistback.entity.Listing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {
}
