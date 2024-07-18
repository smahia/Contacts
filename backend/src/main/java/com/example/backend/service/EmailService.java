package com.example.backend.service;

import com.example.backend.dto.createDto.CreateEmailDto;
import com.example.backend.entity.EmailAddress;
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
