package com.example.backend.controller;

import com.example.backend.dto.ContactDto;
import com.example.backend.dto.createDto.CreateContactDto;
import com.example.backend.dto.updateDto.UpdateContactDto;
import com.example.backend.dtoConverter.ContactDtoConverter;
import com.example.backend.error.ApiError;
import com.example.backend.error.ApiValidationError;
import com.example.backend.service.ContactService;
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
    @GetMapping(path = "/getContacts")
    public ResponseEntity<List<ContactDto>> getAllContacts() {

        return new ResponseEntity<>(contactDtoConverter.convertToDtoList(contactService.getAllContacts()
        ), HttpStatus.OK);
    }

    /**
     * Return a set of contacts of an specific list
     *
     * @param listId The list id
     * @return ResponseEntity<Set < ContactDto>>
     */
    @Operation(summary = "Get contacts by list", responses = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ContactDto.class)))
    })
    @GetMapping(path = "/getContactsByList/{listId}")
    public ResponseEntity<List<ContactDto>> getContactsByList(@PathVariable int listId) {

        return new ResponseEntity<>(contactDtoConverter.convertToDtoList(contactService.getAllContactsByList(listId)),
                HttpStatus.OK);
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
            @ApiResponse(responseCode = "404", description = "Contact not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping(path = "/{id}")
    public ResponseEntity<ContactDto> getContact(@PathVariable int id) {

        ContactDto contactDto = contactDtoConverter.convertToDto(contactService.getContact(id));

        return new ResponseEntity<>(contactDto, HttpStatus.OK);
    }

    /**
     * Add a new contact to a list
     *
     * @param listId           The id of the list the contact will be added to
     * @param createContactDto The object containing the input from the user
     * @return ResponseEntity<ContactDto>
     */
    @Operation(summary = "Create a new Contact and add it to an existent Listing", responses = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ContactDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request: validation fails",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiValidationError.class))),
            @ApiResponse(responseCode = "404", description = "List not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)))
    })
    @PostMapping(path = "/addnewtolist/{listId}")
    public ResponseEntity<ContactDto> addNewContactToList(@PathVariable int listId,
                                                          @Valid @RequestBody CreateContactDto createContactDto) {

        return new ResponseEntity<>(contactDtoConverter.convertToDto(
                contactService.createNewContactInList(createContactDto, listId)), HttpStatus.CREATED);
    }

    /**
     * Method for editing an existing contact
     *
     * @param contactDtoToEdit The ContactDto object containing the data entered by the user
     * @param id               The id of the Contact to edit
     * @return ResponseEntity<ContactDto>
     */
    @Operation(summary = "Edit a contact by ID", responses = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ContactDto.class))),
            @ApiResponse(responseCode = "404", description = "Contact not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))),
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
            @ApiResponse(responseCode = "404", description = "Contact not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)))
    })
    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<?> deleteContact(@PathVariable int id) {

        contactService.deleteContact(id);

        return ResponseEntity.noContent().build();

    }

    /**
     * Delete a contact from a list
     *
     * @param listId    The id of the list from where the contact will be deleted
     * @param contactId The id of the contact that will be deleted
     * @return ResponseEntity<?> No Content
     */
    @Operation(summary = "Delete a contact from a list by ID", responses = {
            @ApiResponse(responseCode = "204", description = "No content",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ContactDto.class))),
            @ApiResponse(responseCode = "404", description = "Contact not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "List not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)))
    })
    @DeleteMapping(path = "deletefromlist/{listId}/{contactId}")
    public ResponseEntity<?> deleteContactFromList(@PathVariable int listId, @PathVariable int contactId) {

        contactService.deleteContactFromList(listId, contactId);

        return ResponseEntity.noContent().build();
    }
}
