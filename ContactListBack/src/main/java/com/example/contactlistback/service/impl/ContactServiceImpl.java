package com.example.contactlistback.service.impl;

import com.example.contactlistback.dto.createDto.CreateContactDto;
import com.example.contactlistback.dto.updateDto.UpdateContactDto;
import com.example.contactlistback.dtoConverter.ContactDtoConverter;
import com.example.contactlistback.entity.Contact;
import com.example.contactlistback.entity.Listing;
import com.example.contactlistback.exception.NotFoundException;
import com.example.contactlistback.repository.ContactRepository;
import com.example.contactlistback.repository.ListingRepository;
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
    private final ListingRepository listingRepository;

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
     * Method that implements the logic for creating a new contact in the databasee
     *
     * @param newContactDto The object containing the data entered by the user
     * @return Contact
     */
    @Override
    public Contact createNewContact(CreateContactDto newContactDto) {

        Contact contact = contactDtoConverter.dtoToNewEntity(newContactDto);

        return contactRepository.save(contact);
    }

    /**
     * Method that updates an existent contact by id
     *
     * @param contactDtoToEdit The object containing the data entered by the user
     * @param id               The ID of the Contact being edited
     * @return Contact with its data already edited
     */
    @Override
    public Contact editContact(UpdateContactDto contactDtoToEdit, int id) {

        Contact existentContact = contactRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Contact not found", id));

        Contact editedContact = contactDtoConverter.dtoToEntity(contactDtoToEdit, existentContact);

        editedContact = contactRepository.save(editedContact);

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

    /**
     * Create a new Contact and add it to a Listing
     *
     * @param createContactDto The object containing the data entered by the user
     * @param listId           The ID of the List to which the contact is being added
     * @return Contact
     * @throws NotFoundException When a list does not exist
     */
    @Override
    public Contact createNewContactInList(CreateContactDto createContactDto, int listId) {

        Listing list = listingRepository.findById(listId).orElseThrow(
                () -> new NotFoundException("List not found", listId));

        /*
        This new Contact does not have an ID because it has not been created yet:
            Contact contact = contactDtoConverter.dtoToNewEntity(createContactDto);
            list.getContactList().add(contact);
       First you have to add it to the database and then, since save returns the contact saved,
       that contact saved added it to the list:
            Contact contact = contactDtoConverter.dtoToNewEntity(createContactDto);
            contact = contactRepository.save(contact);
            list.getContactList().add(contact);
         */

        Contact contact = this.createNewContact(createContactDto);

        list.getContactList().add(contact);

        listingRepository.save(list);

        return contact;
    }

    /**
     * Deletes a Contact from an specific list
     *
     * @param listId    The ID of the list from which the contact will be deleted
     * @param idContact The id of the contact that will be deleted
     * @throws NotFoundException When a list or a contact is not found
     */
    @Override
    public void deleteContactFromList(int listId, int idContact) {

        Listing list = listingRepository.findById(listId).orElseThrow(
                () -> new NotFoundException("List not found", listId));

        Contact existentContact = contactRepository.findById(idContact).orElseThrow(() ->
                new NotFoundException("Contact not found", idContact));

        list.getContactList().remove(existentContact);

        listingRepository.save(list);
    }
}
