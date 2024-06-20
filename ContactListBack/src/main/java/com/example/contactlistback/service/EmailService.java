package com.example.contactlistback.service;

import com.example.contactlistback.dto.EmailDto;
import com.example.contactlistback.entity.EmailAddress;
import org.springframework.stereotype.Service;

/**
 * Class that implements the header methods to handle CRUD operations to manage email data.
 */
@Service
public interface EmailService {

    EmailAddress addEmail(EmailDto emailDto, int idContact);

    EmailAddress editEmail(EmailDto updatedEmailDto, int idEmail);

    void deleteEmail(int id);
}
