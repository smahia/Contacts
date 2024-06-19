package com.example.contactlistback.dtoConverter;

import com.example.contactlistback.dto.EmailDto;
import com.example.contactlistback.entity.Contact;
import com.example.contactlistback.entity.Email;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Class that implements the logic so that a EmailDto can be mapped to a Email object and vice versa, manually
 * or using Model Mapper.
 */
@Component
@RequiredArgsConstructor
public class EmailDtoConverter {

    private final ModelMapper modelMapper;

    /**
     * Converts a list of Email to a list of EmailDto
     * @param emails An arrayList with the emails to be converted to an ArrayList of EmailsDto
     * @return List<EmailDto>
     */
    public List<EmailDto> convertToDtoList(List<Email> emails) {
        return emails.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Converts a Email to a EmailDto using Model Mapper
     * @param email The Email to be converted to a EmailDto
     * @return EmailDto
     */
    public EmailDto convertToDto(Email email) {
        return modelMapper.map(email, EmailDto.class);
    }

    /**
     * Converts a EmailDto to a Email using Model Mapper
     * @param emailDto The EmailDto to be converted to an Email
     * @return Email
     */
    public Email fromDtoToEntity(EmailDto emailDto) {
        return modelMapper.map(emailDto, Email.class);
    }

    /**
     * Converts a EmailDto to a new Email without the Model Mapper
     * @param emailDto The EmailDto which contains the data input from the user
     * @param contact The Contact to be assigned to this Email
     * @return email
     */
    public Email dtoToNewEntity(EmailDto emailDto, Contact contact) {

        Email email = new Email();
        email.setType(emailDto.getType());
        email.setEmail(emailDto.getEmail());
        email.setContact(contact);

        return email;
    }

}
