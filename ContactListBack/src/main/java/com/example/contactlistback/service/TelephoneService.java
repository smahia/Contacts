package com.example.contactlistback.service;

import com.example.contactlistback.dto.createDto.CreateTelephoneDto;
import com.example.contactlistback.entity.Telephone;
import org.springframework.stereotype.Service;

/**
 * Class that implements the header methods to handle CRUD operations to manage telephone data.
 */
@Service
public interface TelephoneService {

    Telephone addTelephone(CreateTelephoneDto telephoneDto, int idContact);

    Telephone editTelephone(CreateTelephoneDto telephoneDtoToEdit, int idTelephone);

    void deleteTelephone(int id);

}
