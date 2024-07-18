package com.example.backend.service;

import com.example.backend.dto.createDto.CreateTelephoneDto;
import com.example.backend.entity.Telephone;
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
