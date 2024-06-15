package com.example.contactlistback.dtoConverter;

import com.example.contactlistback.dto.ContactDto;
import com.example.contactlistback.entity.Contact;
import com.example.contactlistback.entity.Telephone;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ContactDtoConverter {

    private final ModelMapper modelMapper;
    private final TelephoneDtoConverter telephoneDtoConverter;

    /**
     * Converts a list of Contact to a list of ContactsDto
     */
    public List<ContactDto> convertToDtoList(List<Contact> contacts) {

        return contacts.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

    }

    /**
     * Converts a Contact to a Contact Dto
     */
    public ContactDto convertToDto(Contact contact) {

        return modelMapper.map(contact, ContactDto.class);

    }

    /**
     * Converts a ContactDto to a Contact
     */
    public Contact fromDtoToEntity(ContactDto contactDto) {

        return modelMapper.map(contactDto, Contact.class);
    }

    /**
     * Converts a ContactDto to a new Contact without the modelMapper
     */
    public Contact dtoToNewEntity(ContactDto newContactDto) {

        Contact contact = new Contact();
        contact.setName(newContactDto.getName());
        contact.setSurname(newContactDto.getSurname());
        contact.setBirthday(newContactDto.getBirthday());
        contact.setContactEmergency(newContactDto.isContactEmergency());

        List<Telephone> newTelephoneList = new ArrayList<>();

        for (int i = 0; i < newContactDto.getTelephoneList().size(); i++) {
            Telephone telephone = telephoneDtoConverter.dtoToNewEntity(newContactDto.getTelephoneList().get(i), contact);
            newTelephoneList.add(telephone);
        }
        contact.setTelephoneList(newTelephoneList);


        return contact;
    }

    /**
     * Converts an edited ContactDto to an existent Contact without the modelMapper
     */
    public Contact dtoToEntity(ContactDto contactDtoToEdit, Contact existentContact) {

        existentContact.setName(contactDtoToEdit.getName());
        existentContact.setSurname(contactDtoToEdit.getSurname());
        existentContact.setBirthday(contactDtoToEdit.getBirthday());
        existentContact.setContactEmergency(contactDtoToEdit.isContactEmergency());

        existentContact.getTelephoneList().clear();

        List<Telephone> telephonesListUpdated = contactDtoToEdit.getTelephoneList().stream().map(dto -> telephoneDtoConverter.dtoToNewEntity(dto, existentContact)).toList();

        existentContact.getTelephoneList().addAll(telephonesListUpdated);

        return existentContact;

    }
}
