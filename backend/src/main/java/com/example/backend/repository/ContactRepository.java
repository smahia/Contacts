package com.example.backend.repository;

import com.example.backend.entity.Contact;
import com.example.backend.entity.Listing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {

    List<Contact> findAllByListOrderByNameAscSurname(Listing listing);
}
