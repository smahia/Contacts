package com.example.contactlistback.service.impl;

import com.example.contactlistback.dto.ContactDto;
import com.example.contactlistback.dto.createDto.CreateContactDto;
import com.example.contactlistback.dtoConverter.ContactDtoConverter;
import com.example.contactlistback.entity.Contact;
import com.example.contactlistback.exception.NotFoundException;
import com.example.contactlistback.repository.ContactRepository;
import com.example.contactlistback.service.ContactService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Class that implements ContactService
 */
@Service
@AllArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private final ContactDtoConverter contactDtoConverter;

    /**
     * Method that returns all the contacts in the database
     *
     * @return List<Contact>
     */
    @Override
    public List<Contact> getAllContacts() {

        return contactRepository.findAll();
    }

    /**
     * Method that implements the logic for getting a contact by id
     *
     * @param id The id of the Contact to find
     * @return Contact
     * @throws NotFoundException If the Contact cannot be found in the database
     */
    @Override
    public Contact getContact(int id) {

        return contactRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Contact not found", id));
    }

    /**
     * Method that implements the logic for adding a new contact to the databasee
     *
     * @param newContactDto The object containing the data entered by the user
     * @return Contact
     */
    @Override
    public Contact addContact(CreateContactDto newContactDto) {

        Contact contact = contactDtoConverter.dtoToNewEntity(newContactDto);

        contactRepository.save(contact);

        return contact;
    }

    /**
     * Method that updates an existent contact by id
     *
     * @param contactDtoToEdit The object containing the data entered by the user
     * @param id               The ID of the Contact being edited
     * @return Contact with its data already edited
     */
    @Override
    public Contact editContact(CreateContactDto contactDtoToEdit, int id) {

        Contact existentContact = contactRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Contact not found", id));

        Contact editedContact = contactDtoConverter.dtoToEntity(contactDtoToEdit, existentContact);

        contactRepository.save(editedContact);

        return editedContact;

    }

    /**
     * Method that deletes an user by id
     *
     * @param id The id of the contact to be deleted
     */
    @Override
    public void deleteContact(int id) {

        contactRepository.findById(id).ifPresentOrElse(
                contact -> contactRepository.deleteById(id),
                () -> {
                    throw new NotFoundException("Contact not found", id);
                }
        );
    }
}
