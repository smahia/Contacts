package com.example.contactlistback.service.impl;

import com.example.contactlistback.dto.AddressDto;
import com.example.contactlistback.dtoConverter.AddressDtoConverter;
import com.example.contactlistback.entity.Address;
import com.example.contactlistback.entity.Contact;
import com.example.contactlistback.exception.NotFoundException;
import com.example.contactlistback.repository.AddressRepository;
import com.example.contactlistback.repository.ContactRepository;
import com.example.contactlistback.service.AddressService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Class that implements AddressService
 */
@Service
@AllArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final ContactRepository contactRepository;
    private final AddressDtoConverter addressDtoConverter;

    /**
     * Add a new Address to an existent contact
     * @param addressDto The object containing the input from the user
     * @param idContact The ID of the Contact to whom the address will be assigned
     * @return Address
     */
    @Override
    public Address addAddress(AddressDto addressDto, int idContact) {

        Contact contact = contactRepository.findById(idContact).orElseThrow(()
                -> new NotFoundException("Contact not found", idContact));

        Address address = addressDtoConverter.dtoToNewEntity(addressDto, contact);
        addressRepository.save(address);

        return  address;

    }
}
