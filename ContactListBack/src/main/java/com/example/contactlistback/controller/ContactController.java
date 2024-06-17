package com.example.contactlistback.controller;

import com.example.contactlistback.dto.ContactDto;
import com.example.contactlistback.entity.Contact;
import com.example.contactlistback.service.ContactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(summary = "Get contacts")
    @GetMapping(path = "/")
    public ResponseEntity<?> getAllContacts() {

        return contactService.getAllContacts();
    }

    /**
     * Method for getting a contact
     */
    @Operation(summary = "Get contact by ID", responses = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Contact.class))),
            @ApiResponse(responseCode = "404", description = "Contact not found")
    })
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getContact(@PathVariable int id) {

        return contactService.getContact(id);
    }

    /**
     * Method for adding a new contact
     */
    @Operation(summary = "Add a new contact")
    @PostMapping(path = "/add")
    public ResponseEntity<?> addContact(@RequestBody ContactDto newContactDto) {

        return contactService.addContact(newContactDto);
    }

    /**
     * Method for editing an existing contact
     */
    @Operation(summary = "Edit a contact by ID", responses = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Contact.class))),
            @ApiResponse(responseCode = "404", description = "Contact not found")
    })
    @PutMapping(path = "/edit/{id}")
    public ResponseEntity<?> editContact(@RequestBody ContactDto contactDtoToEdit, @PathVariable int id) {

        return contactService.editContact(contactDtoToEdit, id);

    }

    /**
     * Method for deleting a contact
     */
    @Operation(summary = "Delete a contact by ID", responses = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Contact.class))),
            @ApiResponse(responseCode = "404", description = "Contact not found")
    })
    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<?> deleteContact(@PathVariable int id) {

        return contactService.deleteContact(id);

    }
}
