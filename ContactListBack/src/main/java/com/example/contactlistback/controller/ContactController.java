package com.example.contactlistback.controller;

import com.example.contactlistback.dto.ContactDto;
import com.example.contactlistback.dto.createDto.CreateContactDto;
import com.example.contactlistback.dto.updateDto.UpdateContactDto;
import com.example.contactlistback.dtoConverter.ContactDtoConverter;
import com.example.contactlistback.entity.Contact;
import com.example.contactlistback.error.ApiValidationError;
import com.example.contactlistback.service.ContactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contacts")
@Tag(name = "ContactController", description = "Contact management API")
public class ContactController {

    private final ContactService contactService;
    private final ContactDtoConverter contactDtoConverter;

    /**
     * Convert the ArrayList of Contact returned by the service into an ArrayList of ContactDto
     *
     * @return ResponseEntity<List < ContactDto>>
     */
    @Operation(summary = "Get contacts", responses = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ContactDto.class)))
    })
    @GetMapping(path = "/")
    public ResponseEntity<List<ContactDto>> getAllContacts() {

        return new ResponseEntity<>(contactDtoConverter.convertToDtoList(contactService.getAllContacts()
        ), HttpStatus.OK);
    }

    /**
     * Method for getting a contact
     * Convert the Contact returned by the service into a ContactDto
     *
     * @param id The id of the contact to find
     * @return ResponseEntity<ContactDto>
     */
    @Operation(summary = "Get contact by ID", responses = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ContactDto.class))),
            @ApiResponse(responseCode = "404", description = "Contact not found")
    })
    @GetMapping(path = "/{id}")
    public ResponseEntity<ContactDto> getContact(@PathVariable int id) {

        ContactDto contactDto = contactDtoConverter.convertToDto(contactService.getContact(id));

        return new ResponseEntity<>(contactDto, HttpStatus.OK);
    }

    /**
     * Method for adding a new contact
     * Convert the Contact returned by the service to a ContactDto
     *
     * @param newContactDto The object containing the data entered by the user
     * @return ResponseEntity<ContactDto>
     */
    @Operation(summary = "Add a new Contact", responses = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ContactDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request: validation fails",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiValidationError.class)))
    })
    @PostMapping(path = "/add")
    public ResponseEntity<ContactDto> addContact(@Valid @RequestBody CreateContactDto newContactDto) {

        Contact contact = contactService.addContact(newContactDto);

        return new ResponseEntity<>(contactDtoConverter.convertToDto(contact), HttpStatus.CREATED);

    }

    /**
     * Method for editing an existing contact
     *
     * @param contactDtoToEdit The ContactDto object containing the data entered by the user
     * @param id               The id of the Contact to edit
     * @return ResponseEntity<ContactDto>
     */
    @Operation(summary = "Edit a contact by ID", responses = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ContactDto.class))),
            @ApiResponse(responseCode = "404", description = "Contact not found"),
            @ApiResponse(responseCode = "400", description = "Bad request: validation fails",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiValidationError.class)))
    })
    @PutMapping(path = "/edit/{id}")
    public ResponseEntity<ContactDto> editContact(@Valid @RequestBody UpdateContactDto contactDtoToEdit, @PathVariable int id) {

        return new ResponseEntity<>(contactDtoConverter.convertToDto(contactService.editContact(contactDtoToEdit, id)),
                HttpStatus.OK);

    }

    /**
     * Method for deleting a contact by id
     *
     * @param id The id of the contact to be deleted
     * @return ResponseEntity<?> No content
     */
    @Operation(summary = "Delete a contact by ID", responses = {
            @ApiResponse(responseCode = "204", description = "No content", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ContactDto.class))),
            @ApiResponse(responseCode = "404", description = "Contact not found")
    })
    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<?> deleteContact(@PathVariable int id) {

        contactService.deleteContact(id);

        return ResponseEntity.noContent().build();

    }
}
