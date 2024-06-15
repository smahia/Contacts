package com.example.contactlistback.service;

import com.example.contactlistback.dto.TelephoneDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface TelephoneService {

    ResponseEntity<?> addTelephone(TelephoneDto telephoneDto, int idContact);

    ResponseEntity<?> editTelephone(TelephoneDto telephoneDtoToEdit, int idTelephone);

    ResponseEntity<?> deleteTelephone(int id);

}
