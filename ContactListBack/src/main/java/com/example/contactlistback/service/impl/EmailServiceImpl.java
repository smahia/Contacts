package com.example.contactlistback.service.impl;

import com.example.contactlistback.dto.EmailDto;
import com.example.contactlistback.dtoConverter.EmailDtoConverter;
import com.example.contactlistback.entity.Email;
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
    private final EmailDtoConverter emailDtoConverter;


    @Override
    public Email addEmail(EmailDto emailDto, int idContact) {
        return null;
    }

    @Override
    public Email editEmail(EmailDto updatedEmailDto, int idContact) {
        return null;
    }

    @Override
    public void deleteEmail(int id) {

    }
}
