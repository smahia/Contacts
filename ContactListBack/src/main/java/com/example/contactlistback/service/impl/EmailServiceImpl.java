package com.example.contactlistback.service.impl;

import com.example.contactlistback.dto.EmailDto;
import com.example.contactlistback.dto.createDto.CreateEmailDto;
import com.example.contactlistback.dtoConverter.EmailDtoConverter;
import com.example.contactlistback.entity.Contact;
import com.example.contactlistback.entity.Email;
import com.example.contactlistback.exception.NotFoundException;
import com.example.contactlistback.repository.ContactRepository;
import com.example.contactlistback.repository.EmailRepository;
import com.example.contactlistback.service.EmailService;
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
     * @param emailDto The new data Email
     * @param idContact The ID of the Contact to whom the Email will be assigned
     * @return Email
     */
    @Override
    public Email addEmail(EmailDto emailDto, int idContact) {

        Contact contact = contactRepository.findById(idContact).orElseThrow(() ->
                new NotFoundException("Contact not found", idContact));

        Email email = emailDtoConverter.dtoToNewEntity(emailDto, contact);
        emailRepository.save(email);

        return email;
    }

    /**
     * Method that updates an existent Email
     * @param updatedEmailDto The Email with the new data entered by the user
     * @param idEmail The ID of the Email that will be edited
     * @return Email
     */
    @Override
    public Email editEmail(EmailDto updatedEmailDto, int idEmail) {

        Email email = emailRepository.findById(idEmail).orElseThrow(() ->
                new NotFoundException("Email not found", idEmail));

        email.setEmail(updatedEmailDto.getEmail());
        email.setType(updatedEmailDto.getType());

        return email;

    }

    /**
     * Method that deletes an Email by id
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
