package com.example.contactlistback.service.impl;

import com.example.contactlistback.dto.TelephoneDto;
import com.example.contactlistback.dtoConverter.TelephoneDtoConverter;
import com.example.contactlistback.entity.Contact;
import com.example.contactlistback.entity.Telephone;
import com.example.contactlistback.repository.ContactRepository;
import com.example.contactlistback.repository.TelephoneRepository;
import com.example.contactlistback.service.TelephoneService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TelephoneServiceImpl implements TelephoneService {

    private final TelephoneRepository telephoneRepository;
    private final ContactRepository contactRepository;
    private final TelephoneDtoConverter telephoneDtoConverter;

    @Override
    public ResponseEntity<?> addTelephone(TelephoneDto telephoneDto, int idContact) {

        Contact contact = contactRepository.findById(idContact).orElse(null);

        if (contact != null) {

            Telephone telephone = telephoneDtoConverter.dtoToNewEntity(telephoneDto, contact);
            telephoneRepository.save(telephone);

            return ResponseEntity.status(HttpStatus.CREATED).body(telephoneDtoConverter.convertToDto(telephone));

        } else {

            return ResponseEntity.notFound().build();
        }

    }

    @Override
    public ResponseEntity<?> editTelephone(TelephoneDto telephoneDtoToEdit, int idTelephone) {

        Telephone telephone = telephoneRepository.findById(idTelephone).orElse(null);

        if (telephone != null) {

            telephone.setTelephoneNumber(telephoneDtoToEdit.getTelephoneNumber());
            telephone.setType(telephoneDtoToEdit.getType());

            telephoneRepository.save(telephone);

            return ResponseEntity.ok(telephoneDtoConverter.convertToDto(telephone));

        } else {

            return ResponseEntity.notFound().build();

        }

    }

    @Override
    public ResponseEntity<?> deleteTelephone(int id) {

        telephoneRepository.findById(id).ifPresent(telephone -> telephoneRepository.deleteById(id));

        return ResponseEntity.noContent().build();
    }

}
