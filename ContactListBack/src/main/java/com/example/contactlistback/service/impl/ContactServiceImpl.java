package com.example.contactlistback.service.impl;

import com.example.contactlistback.dto.ContactDto;
import com.example.contactlistback.dtoConverter.ContactDtoConverter;
import com.example.contactlistback.entity.Contact;
import com.example.contactlistback.repository.ContactRepository;
import com.example.contactlistback.service.ContactService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private final ContactDtoConverter contactDtoConverter;

    @Override
    public ResponseEntity<?> getAllContacts() {

        List<Contact> contacts = contactRepository.findAll();

        if (contacts.isEmpty()) {
            return ResponseEntity.notFound().build();

        } else {
            List<ContactDto> contactDtoList = contactDtoConverter.convertToDtoList(contacts);

            return ResponseEntity.ok(contactDtoList);
        }
    }

    @Override
    public ResponseEntity<?> getContact(int id) {

        Contact contact = contactRepository.findById(id).orElse(null);

        if (contact == null) {
            return ResponseEntity.notFound().build();

        } else {
            ContactDto contactDto = contactDtoConverter.convertToDto(contact);
            return ResponseEntity.ok(contactDto);
        }
    }

    @Override
    public ResponseEntity<?> addContact(ContactDto newContactDto) {

        Contact contact = contactDtoConverter.dtoToNewEntity(newContactDto);

        contactRepository.save(contact);

        return ResponseEntity.status(HttpStatus.CREATED).body(contactDtoConverter.convertToDto(contact));
    }

    @Override
    public ResponseEntity<?> editContact(ContactDto contactDtoToEdit, int id) {

        Contact existentContact = contactRepository.findById(id).orElse(null);

        if (existentContact != null) {

            Contact editedContact = contactDtoConverter.dtoToEntity(contactDtoToEdit, existentContact);

            contactRepository.save(editedContact);

            return ResponseEntity.status(HttpStatus.OK).body(contactDtoConverter.convertToDto(editedContact));

        } else {

            return ResponseEntity.notFound().build();
        }

    }

    @Override
    public ResponseEntity<?> deleteContact(int id) {

        contactRepository.deleteById(id);

        return ResponseEntity.noContent().build();

    }
}
