package com.example.contactlistback.controller;

import com.example.contactlistback.dto.ContactDto;
import com.example.contactlistback.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contacts")
public class ContactController {

    private final ContactService contactService;

    /**
     * Method for getting all contacts
     */
    @GetMapping(path = "/")
    public ResponseEntity<?> getAllContacts() {

        return contactService.getAllContacts();
    }

    /**
     * Method for getting a contact
     */
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getContact(@PathVariable int id) {

        return contactService.getContact(id);
    }

    /**
     * Method for adding a new contact
     */
    @PostMapping(path = "/add")
    public ResponseEntity<?> addContact(@RequestBody ContactDto newContactDto) {

        return contactService.addContact(newContactDto);
    }

    /**
     * Method for editing an existing contact
     */
    @PutMapping(path = "/edit/{id}")
    public ResponseEntity<?> editContact(@RequestBody ContactDto contactDtoToEdit, @PathVariable int id) {

        return contactService.editContact(contactDtoToEdit, id);

    }

    /**
     * Method for deleting a contact
     */
    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<?> deleteContact(@PathVariable int id) {

        return contactService.deleteContact(id);

    }
}
