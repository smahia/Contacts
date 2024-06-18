package com.example.contactlistback.service.impl;

import com.example.contactlistback.dto.TelephoneDto;
import com.example.contactlistback.dtoConverter.TelephoneDtoConverter;
import com.example.contactlistback.entity.Contact;
import com.example.contactlistback.entity.Telephone;
import com.example.contactlistback.exception.NotFoundException;
import com.example.contactlistback.repository.ContactRepository;
import com.example.contactlistback.repository.TelephoneRepository;
import com.example.contactlistback.service.TelephoneService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Class that implements TelephoneService
 */
@Service
@AllArgsConstructor
public class TelephoneServiceImpl implements TelephoneService {

    private final TelephoneRepository telephoneRepository;
    private final ContactRepository contactRepository;
    private final TelephoneDtoConverter telephoneDtoConverter;

    /**
     * Method for adding a new Telephone to the database
     * @param telephoneDto The TelephoneDto containing the data entered by the user
     * @param idContact The id of the contact to which the phone will be assigned
     * @return Telephone
     */
    @Override
    public Telephone addTelephone(TelephoneDto telephoneDto, int idContact) {

        Contact contact = contactRepository.findById(idContact).orElseThrow(()
                -> new NotFoundException("Contact not found", idContact));

        Telephone telephone = telephoneDtoConverter.dtoToNewEntity(telephoneDto, contact);
        telephoneRepository.save(telephone);

        return telephone;

    }

    /**
     *
     * @param telephoneDtoToEdit The TelephoneDto existent in the database to be edited and
     * that contains the new data entered by the user
     * @param idTelephone The id of the telephone to be edited
     * @return Telephone
     */
    @Override
    public Telephone editTelephone(TelephoneDto telephoneDtoToEdit, int idTelephone) {

        Telephone telephone = telephoneRepository.findById(idTelephone).orElseThrow(() ->
                new NotFoundException("Telephone not found", idTelephone));

        telephone.setTelephoneNumber(telephoneDtoToEdit.getTelephoneNumber());
        telephone.setType(telephoneDtoToEdit.getType());

        telephoneRepository.save(telephone);

        return telephone;
    }

    /**
     * Method for deleting a Telephone
     * @param id The id of the Telephone to be deleted
     */
    @Override
    public void deleteTelephone(int id) {
        //telephoneRepository.findById(id).ifPresent(telephone -> telephoneRepository.deleteById(id));
        telephoneRepository.findById(id).ifPresentOrElse(
                telephone -> telephoneRepository.deleteById(id),
                () -> {
                    throw new NotFoundException("Telephone not found", id);
                }
        );

    }

}
