package com.example.contactlistback.service;

import com.example.contactlistback.dto.ContactDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface ContactService {

    ResponseEntity<?> getAllContacts();

    ResponseEntity<?> getContact(int id);

    ResponseEntity<?> addContact(ContactDto newContactDto);

    ResponseEntity<?> editContact(ContactDto contactDtoToEdit, int id);

    ResponseEntity<?> deleteContact(int id);

}
