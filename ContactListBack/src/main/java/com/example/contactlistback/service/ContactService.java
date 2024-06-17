package com.example.contactlistback.service;

import com.example.contactlistback.dto.ContactDto;
import com.example.contactlistback.entity.Contact;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Class that implements the header methods to handle CRUD operations to manage contact data.
 */
@Service
public interface ContactService {

    List<Contact> getAllContacts();

    Contact getContact(int id);

    Contact addContact(ContactDto newContactDto);

    Contact editContact(ContactDto contactDtoToEdit, int id);

    ResponseEntity<?> deleteContact(int id);

}
