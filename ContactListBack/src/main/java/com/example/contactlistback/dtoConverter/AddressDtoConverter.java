package com.example.contactlistback.dtoConverter;

import com.example.contactlistback.dto.AddressDto;
import com.example.contactlistback.entity.Address;
import com.example.contactlistback.entity.Contact;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Class that implements the logic so that a AddressDto can be mapped to an Address object and vice versa, manually
 * or using Model Mapper.
 */
@Component
@RequiredArgsConstructor
public class AddressDtoConverter {

    private final ModelMapper modelMapper;

    /**
     * Converts a list of Address to a list of AddressDto
     * @param addresses An arrayList with the addresses to be converted to an ArrayList of AddressDto
     * @return List<AddressDto>
     */
    public List<AddressDto> convertToDtoList(List<Address> addresses) {
        return addresses.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Converts an Address to a AddressDto using Model Mapper
     * @param address The Address to be converted to AddressDto
     * @return AddressDto
     */
    public AddressDto convertToDto(Address address) {
        return modelMapper.map(address, AddressDto.class);
    }

    /**
     * Converts an AddressDto to an Address using Model Mapper
     * @param addressDto The AddressDto to be converted to Address
     * @return Address
     */
    public Address fromDtoToEntity(AddressDto addressDto) {
        return modelMapper.map(addressDto, Address.class);
    }

    /**
     * Converts a AddressDto to a new Address without the Model Mapper
     * @param addressDto The AddressDto which contains the data input from the user
     * @param contact The Contact to be assigned to this Address
     * @return Address
     */
    public Address dtoToNewEntity(AddressDto addressDto, Contact contact) {

        Address address = new Address();
        address.setType(addressDto.getType());
        address.setAddress(addressDto.getAddress());
        address.setContact(contact);

        return address;
    }

}
