package com.example.contactlistback.dtoConverter;

import com.example.contactlistback.dto.TelephoneDto;
import com.example.contactlistback.entity.Contact;
import com.example.contactlistback.entity.Telephone;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TelephoneDtoConverter {

    private final ModelMapper modelMapper;

    /**
     * Converts a list of Telephone to a list of TelephoneDto
     */
    public List<TelephoneDto> convertToDtoList(List<Telephone> telephones) {
        return telephones.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Converts a Telephone to a TelephoneDto
     */
    public TelephoneDto convertToDto(Telephone telephone) {
        return modelMapper.map(telephone, TelephoneDto.class);
    }

    /**
     * Converts a TelephoneDto to a Telephone
     */
    public Telephone fromDtoToEntity(TelephoneDto telephoneDto) {
        return modelMapper.map(telephoneDto, Telephone.class);
    }

    /**
     * Converts a TelephoneDto to a new Telephone without the Model Mapper
     */
    public Telephone dtoToNewEntity(TelephoneDto telephoneDto, Contact contact) {

        Telephone telephone = new Telephone();
        telephone.setType(telephoneDto.getType());
        telephone.setTelephoneNumber(telephoneDto.getTelephoneNumber());
        telephone.setContact(contact);

        return telephone;
    }

}
