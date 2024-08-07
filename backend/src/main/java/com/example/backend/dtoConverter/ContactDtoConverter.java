package com.example.backend.dtoConverter;

import com.example.backend.dto.ContactDto;
import com.example.backend.dto.createDto.CreateContactDto;
import com.example.backend.dto.updateDto.UpdateContactDto;
import com.example.backend.entity.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Class that implements the logic so that a ContactDto can be mapped to a Contact object and vice versa, manually or
 * using Model Mapper.
 */
@Component
@RequiredArgsConstructor
public class ContactDtoConverter {

    private final ModelMapper modelMapper;
    private final TelephoneDtoConverter telephoneDtoConverter;
    private final AddressDtoConverter addressDtoConverter;
    private final EmailDtoConverter emailDtoConverter;

    /**
     * Converts a list of Contact to a list of ContactsDto using Model Mapper
     *
     * @param contacts An ArrayList with the contacts to be converted to an ArrayList of ContactDto
     * @return List<Contact>
     */
    public List<ContactDto> convertToDtoList(List<Contact> contacts) {

        return contacts.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

    }

    /**
     * Converts a set of Contact to a set of ContactsDto using Model Mapper
     *
     * @param contacts A set with the contacts to be converted to a set of ContactDto
     * @return Set<Contact>
     */
    public Set<ContactDto> convertToDtoSet(Set<Contact> contacts) {

        return contacts.stream()
                .map(this::convertToDto)
                .collect(Collectors.toSet());
    }

    /**
     * Converts a Contact to a Contact Dto using Model Mapper
     * Get the listings of the contacts and then only the id
     * It has to be set manually because in the Entity is a Set of Listings but in the Dto is a Set of IDs, so the
     * Model Mapper does not know how to do the association
     *
     * @param contact The contact to be converted to DTO
     * @return ContactDto
     */
    public ContactDto convertToDto(Contact contact) {

        //return modelMapper.map(contact, ContactDto.class);
        ContactDto contactDto = modelMapper.map(contact, ContactDto.class);

        int listId = contact.getList().getId();

        contactDto.setListId(listId);

        return contactDto;

    }

    /**
     * Converts a ContactDto to a Contact using Model Mapper
     *
     * @param contactDto The ContactDto to be converted to Contact
     * @return Contact
     */
    public Contact fromDtoToEntity(ContactDto contactDto) {

        return modelMapper.map(contactDto, Contact.class);
    }

    /**
     * Converts a ContactDto to a new Contact without the modelMapper
     *
     * @param newContactDto The new ContactDto to be converted to a Contact manually
     * @param list          The list to which the contact will be added
     * @return Contact
     */
    public Contact dtoToNewEntity(CreateContactDto newContactDto, Listing list) {

        Contact contact = new Contact();
        contact.setName(newContactDto.getName());
        contact.setSurname(newContactDto.getSurname());
        contact.setBirthday(newContactDto.getBirthday());
        contact.setContactEmergency(newContactDto.getContactEmergency());
        contact.setList(list);

        // Add Telephone
        List<Telephone> newTelephoneList = new ArrayList<>();

        for (int i = 0; i < newContactDto.getTelephoneList().size(); i++) {
            Telephone telephone = telephoneDtoConverter.dtoToNewEntity(newContactDto.getTelephoneList().get(i), contact);
            newTelephoneList.add(telephone);
        }
        contact.setTelephoneList(newTelephoneList);

        // Add Address
        List<Address> newAddressList = new ArrayList<>();

        for (int i = 0; i < newContactDto.getAddressesList().size(); i++) {
            Address address = addressDtoConverter.dtoToNewEntity(newContactDto.getAddressesList().get(i), contact);
            newAddressList.add(address);
        }
        contact.setAddressesList(newAddressList);

        // Add Email
        List<EmailAddress> newEmailList = new ArrayList<>();

        for (int i = 0; i < newContactDto.getEmailList().size(); i++) {
            EmailAddress email = emailDtoConverter.dtoToNewEntity(newContactDto.getEmailList().get(i), contact);
            newEmailList.add(email);
        }
        contact.setEmailList(newEmailList);

        return contact;
    }

    /**
     * Converts an edited ContactDto to an existent Contact without the modelMapper
     * Only updates personal details of the contact (not telephone, email or address)
     *
     * @param contactDtoToEdit The ContactDto which contains the input data from the user
     * @param existentContact  The current contact that exists in the database
     * @return Contact
     */
    // TODO: change the CreateDTO to UpdateDto? in ALL Update methods
    public Contact dtoToEntity(UpdateContactDto contactDtoToEdit, Contact existentContact) {

        existentContact.setName(contactDtoToEdit.getName());
        existentContact.setSurname(contactDtoToEdit.getSurname());
        existentContact.setBirthday(contactDtoToEdit.getBirthday());
        existentContact.setContactEmergency(contactDtoToEdit.getContactEmergency());

        // TODO: WHEN UPDATING AN ELEMENT, THE ID OF THE TELEPHONE AND SO CHANGE AS WELL
        ///////////////////////////////
        /*existentContact.getTelephoneList().clear();

        List<Telephone> telephonesListUpdated = contactDtoToEdit.getTelephoneList().stream()
                .map(dto -> telephoneDtoConverter.dtoToNewEntity(dto, existentContact)).toList();

        existentContact.getTelephoneList().addAll(telephonesListUpdated);*/

        ///////////////////////////////

        /*existentContact.getAddressesList().clear();

        List<Address> addressesListUpdated = contactDtoToEdit.getAddressesList().stream()
                .map(dto -> addressDtoConverter.dtoToNewEntity(dto, existentContact)).toList();

        existentContact.getAddressesList().addAll(addressesListUpdated);*/

        ///////////////////////////////
        /*existentContact.getEmailList().clear();

        List<EmailAddress> emailListUpdated = contactDtoToEdit.getEmailList().stream()
                .map(dto -> emailDtoConverter.dtoToNewEntity(dto, existentContact)).toList();

        existentContact.getEmailList().addAll(emailListUpdated);*/

        return existentContact;

    }
}
