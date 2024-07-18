package com.example.backend.service;

import com.example.backend.dto.createDto.CreateContactDto;
import com.example.backend.dto.updateDto.UpdateContactDto;
import com.example.backend.entity.Contact;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * Class that implements the header methods to handle CRUD operations to manage contact data.
 */
@Service
public interface ContactService {

    List<Contact> getAllContacts();

    Set<Contact> getAllContactsByList(int listId);

    Contact getContact(int id);

    Contact createNewContact(CreateContactDto newContactDto);

    Contact editContact(UpdateContactDto contactDtoToEdit, int id);

    void deleteContact(int id);

    Contact createNewContactInList(CreateContactDto createContactDto, int listId);

    void addContactToList(int contactId, int listId);

    void deleteContactFromList(int listId, int contactId);

    void moveContactBetweenLists(int sourceListId, int destinationListId, int contactId);

}
