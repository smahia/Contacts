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

/**
 * Class that implements the logic so that a TelephoneDto can be mapped to a Telephone object and vice versa, manually
 * or using Model Mapper.
 */
@Component
@RequiredArgsConstructor
public class TelephoneDtoConverter {

    private final ModelMapper modelMapper;

    /**
     * Converts a list of Telephone to a list of TelephoneDto
     * @param telephones An arrayList with the telephones to be converted to an ArrayList of TelephonesDTO
     * @return List<TelephonesDto>
     */
    public List<TelephoneDto> convertToDtoList(List<Telephone> telephones) {
        return telephones.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Converts a Telephone to a TelephoneDto using Model Mapper
     * @param telephone The Telephone to be converted to TelephoneDto
     * @return TelephoneDto
     */
    public TelephoneDto convertToDto(Telephone telephone) {
        return modelMapper.map(telephone, TelephoneDto.class);
    }

    /**
     * Converts a TelephoneDto to a Telephone using Model Mapper
     * @param telephoneDto The TelephoneDto to be converted to Telephone
     * @return Telephone
     */
    public Telephone fromDtoToEntity(TelephoneDto telephoneDto) {
        return modelMapper.map(telephoneDto, Telephone.class);
    }

    /**
     * Converts a TelephoneDto to a new Telephone without the Model Mapper
     * @param telephoneDto The TelephoneDto which contains the data input from the user
     * @param contact The Contact to be assigned to this Telephone
     * @return Telephone
     */
    public Telephone dtoToNewEntity(TelephoneDto telephoneDto, Contact contact) {

        Telephone telephone = new Telephone();
        telephone.setType(telephoneDto.getType());
        telephone.setTelephoneNumber(telephoneDto.getTelephoneNumber());
        telephone.setContact(contact);

        return telephone;
    }

}
