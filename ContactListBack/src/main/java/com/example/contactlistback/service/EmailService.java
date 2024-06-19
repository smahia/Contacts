package com.example.contactlistback.service;

import com.example.contactlistback.dto.EmailDto;
import com.example.contactlistback.entity.Email;
import org.springframework.stereotype.Service;

/**
 * Class that implements the header methods to handle CRUD operations to manage email data.
 */
@Service
public interface EmailService {

    Email addEmail(EmailDto emailDto, int idContact);

    Email editEmail(EmailDto updatedEmailDto, int idContact);

    void deleteEmail(int id);
}
