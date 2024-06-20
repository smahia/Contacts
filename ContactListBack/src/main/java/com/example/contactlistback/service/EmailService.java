package com.example.contactlistback.service;

import com.example.contactlistback.dto.createDto.CreateEmailDto;
import com.example.contactlistback.entity.EmailAddress;
import org.springframework.stereotype.Service;

/**
 * Class that implements the header methods to handle CRUD operations to manage email data.
 */
@Service
public interface EmailService {

    EmailAddress addEmail(CreateEmailDto emailDto, int idContact);

    EmailAddress editEmail(CreateEmailDto updatedEmailDto, int idEmail);

    void deleteEmail(int id);
}
