package com.example.backend.service.impl;

import com.example.backend.dto.createDto.CreateAddressDto;
import com.example.backend.dtoConverter.AddressDtoConverter;
import com.example.backend.entity.Address;
import com.example.backend.entity.Contact;
import com.example.backend.exception.NotFoundException;
import com.example.backend.repository.AddressRepository;
import com.example.backend.repository.ContactRepository;
import com.example.backend.service.AddressService;
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
     *
     * @param addressDto The object containing the input from the user
     * @param ContactId  The ID of the Contact to whom the address will be assigned
     * @return Address
     */
    @Override
    public Address addAddress(CreateAddressDto addressDto, int ContactId) {

        Contact contact = contactRepository.findById(ContactId).orElseThrow(()
                -> new NotFoundException("Contact not found", ContactId));

        Address address = addressDtoConverter.dtoToNewEntity(addressDto, contact);

        return addressRepository.save(address);

    }

    @Override
    public Address editAddress(CreateAddressDto addressDto, int AddressId) {

        Address address = addressRepository.findById(AddressId).orElseThrow(()
                -> new NotFoundException("Address not found", AddressId));

        address.setAddress(addressDto.getAddress());
        address.setType(addressDto.getType());


        return addressRepository.save(address);
    }

    /**
     * Deletes an Address by ID
     *
     * @param id The ID of the Address to be deleted
     */
    @Override
    public void deleteAddress(int id) {

        addressRepository.findById(id).ifPresentOrElse(
                address ->
                        addressRepository.deleteById(id),
                () -> {
                    throw new NotFoundException("Address not found", id);
                }
        );

    }
}
