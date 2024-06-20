package com.example.contactlistback.service;

import com.example.contactlistback.dto.AddressDto;
import com.example.contactlistback.entity.Address;
import org.springframework.stereotype.Service;

/**
 * Class that implements the header methods to handle CRUD operations to manage addresses data.
 */
@Service
public interface AddressService {

    Address addAddress(AddressDto addressDto, int contactId);
}
