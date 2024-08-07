package com.example.backend.service;

import com.example.backend.dto.createDto.CreateAddressDto;
import com.example.backend.entity.Address;
import org.springframework.stereotype.Service;

/**
 * Class that implements the header methods to handle CRUD operations to manage addresses data.
 */
@Service
public interface AddressService {

    Address addAddress(CreateAddressDto addressDto, int contactId);

    Address editAddress(CreateAddressDto addressDto, int addressId);

    void deleteAddress(int id);
}
