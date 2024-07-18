package com.example.backend.service.impl;

import com.example.backend.dto.createDto.CreateTelephoneDto;
import com.example.backend.dtoConverter.TelephoneDtoConverter;
import com.example.backend.entity.Contact;
import com.example.backend.entity.Telephone;
import com.example.backend.exception.NotFoundException;
import com.example.backend.repository.ContactRepository;
import com.example.backend.repository.TelephoneRepository;
import com.example.backend.service.TelephoneService;
import lombok.AllArgsConstructor;
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
     *
     * @param telephoneDto The TelephoneDto containing the data entered by the user
     * @param idContact    The id of the contact to which the phone will be assigned
     * @return Telephone
     */
    @Override
    public Telephone addTelephone(CreateTelephoneDto telephoneDto, int idContact) {

        Contact contact = contactRepository.findById(idContact).orElseThrow(()
                -> new NotFoundException("Contact not found", idContact));

        Telephone telephone = telephoneDtoConverter.dtoToNewEntity(telephoneDto, contact);


        return telephoneRepository.save(telephone);

    }

    /**
     * @param telephoneDtoToEdit The TelephoneDto existent in the database to be edited and
     *                           that contains the new data entered by the user
     * @param idTelephone        The id of the telephone to be edited
     * @return Telephone
     */
    @Override
    public Telephone editTelephone(CreateTelephoneDto telephoneDtoToEdit, int idTelephone) {

        Telephone telephone = telephoneRepository.findById(idTelephone).orElseThrow(() ->
                new NotFoundException("Telephone not found", idTelephone));

        telephone.setTelephoneNumber(telephoneDtoToEdit.getTelephoneNumber());
        telephone.setType(telephoneDtoToEdit.getType());

        return telephoneRepository.save(telephone);
    }

    /**
     * Method for deleting a Telephone
     *
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
