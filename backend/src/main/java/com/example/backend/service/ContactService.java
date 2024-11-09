package com.example.backend.service;

import com.example.backend.dto.createDto.CreateContactDto;
import com.example.backend.dto.updateDto.UpdateContactDto;
import com.example.backend.entity.Contact;
import com.example.backend.entity.Listing;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Class that implements the header methods to handle CRUD operations to manage contact data.
 */
@Service
public interface ContactService {

    List<Contact> getAllContacts();

    List<Contact> getAllContactsByList(int listId);

    Contact getContact(int id);

    Contact createNewContact(CreateContactDto newContactDto, Listing list);

    Contact editContact(UpdateContactDto contactDtoToEdit, int id);

    void deleteContact(int id);

    Contact createNewContactInList(CreateContactDto createContactDto, int listId);

    void deleteContactFromList(int listId, int contactId);

}
