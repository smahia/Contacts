package com.example.contactlistback.dtoConverter;

import com.example.contactlistback.dto.ContactDto;
import com.example.contactlistback.dto.createDto.CreateContactDto;
import com.example.contactlistback.dto.updateDto.UpdateContactDto;
import com.example.contactlistback.entity.Address;
import com.example.contactlistback.entity.Contact;
import com.example.contactlistback.entity.EmailAddress;
import com.example.contactlistback.entity.Telephone;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
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
     * Converts a Contact to a Contact Dto using Model Mapper
     *
     * @param contact The contact to be converted to DTO
     * @return ContactDto
     */
    public ContactDto convertToDto(Contact contact) {

        return modelMapper.map(contact, ContactDto.class);

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
     * @return Contact
     */
    public Contact dtoToNewEntity(CreateContactDto newContactDto) {

        Contact contact = new Contact();
        contact.setName(newContactDto.getName());
        contact.setSurname(newContactDto.getSurname());
        contact.setBirthday(newContactDto.getBirthday());
        contact.setContactEmergency(newContactDto.getContactEmergency());

        ///////////////////////////////
        List<Telephone> newTelephoneList = new ArrayList<>();

        for (int i = 0; i < newContactDto.getTelephoneList().size(); i++) {
            Telephone telephone = telephoneDtoConverter.dtoToNewEntity(newContactDto.getTelephoneList().get(i), contact);
            newTelephoneList.add(telephone);
        }
        contact.setTelephoneList(newTelephoneList);

        ///////////////////////////////
        List<Address> newAddressList = new ArrayList<>();

        for (int i = 0; i < newContactDto.getAddressesList().size(); i++) {
            Address address = addressDtoConverter.dtoToNewEntity(newContactDto.getAddressesList().get(i), contact);
            newAddressList.add(address);
        }
        contact.setAddressesList(newAddressList);

        ///////////////////////////////
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
