package com.example.contactlistback.service;

import com.example.contactlistback.dto.createDto.CreateContactDto;
import com.example.contactlistback.dto.updateDto.UpdateContactDto;
import com.example.contactlistback.entity.Contact;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Class that implements the header methods to handle CRUD operations to manage contact data.
 */
@Service
public interface ContactService {

    List<Contact> getAllContacts();

    Contact getContact(int id);

    Contact addContact(CreateContactDto newContactDto);

    Contact editContact(UpdateContactDto contactDtoToEdit, int id);

    void deleteContact(int id);

    // TODO: methods for adding, deleting an moving a contact to a list

}
