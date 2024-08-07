package com.example.backend.service.impl;

import com.example.backend.dto.createDto.CreateEmailDto;
import com.example.backend.dtoConverter.EmailDtoConverter;
import com.example.backend.entity.Contact;
import com.example.backend.entity.EmailAddress;
import com.example.backend.exception.NotFoundException;
import com.example.backend.repository.ContactRepository;
import com.example.backend.repository.EmailRepository;
import com.example.backend.service.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Class that implements EmailService
 */
@Service
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final EmailRepository emailRepository;
    private final ContactRepository contactRepository;
    private final EmailDtoConverter emailDtoConverter;


    /**
     * Method that adds a new Email to an existent contact
     *
     * @param emailDto  The new data Email
     * @param idContact The ID of the Contact to whom the Email will be assigned
     * @return Email
     */
    @Override
    public EmailAddress addEmail(CreateEmailDto emailDto, int idContact) {

        Contact contact = contactRepository.findById(idContact).orElseThrow(() ->
                new NotFoundException("Contact not found", idContact));

        EmailAddress email = emailDtoConverter.dtoToNewEntity(emailDto, contact);

        return emailRepository.save(email);
    }

    /**
     * Method that updates an existent Email
     *
     * @param updatedEmailDto The Email with the new data entered by the user
     * @param idEmail         The ID of the Email that will be edited
     * @return Email
     */
    @Override
    public EmailAddress editEmail(CreateEmailDto updatedEmailDto, int idEmail) {

        EmailAddress email = emailRepository.findById(idEmail).orElseThrow(() ->
                new NotFoundException("Email not found", idEmail));

        email.setEmail(updatedEmailDto.getEmail());
        email.setType(updatedEmailDto.getType());

        return emailRepository.save(email);

    }

    /**
     * Method that deletes an Email by id
     *
     * @param id The ID of the Email to be deleted
     */
    @Override
    public void deleteEmail(int id) {

        emailRepository.findById(id).ifPresentOrElse(
                email ->
                        emailRepository.deleteById(id),
                () -> {
                    throw new NotFoundException("Email not found", id);
                }
        );
    }
}
