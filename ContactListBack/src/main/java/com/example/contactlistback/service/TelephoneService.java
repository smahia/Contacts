package com.example.contactlistback.service;

import com.example.contactlistback.dto.TelephoneDto;
import com.example.contactlistback.entity.Telephone;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Class that implements the header methods to handle CRUD operations to manage telephone data.
 */
@Service
public interface TelephoneService {

    Telephone addTelephone(TelephoneDto telephoneDto, int idContact);

    Telephone editTelephone(TelephoneDto telephoneDtoToEdit, int idTelephone);

    void deleteTelephone(int id);

}
