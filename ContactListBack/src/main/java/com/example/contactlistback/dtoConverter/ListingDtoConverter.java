package com.example.contactlistback.dtoConverter;

import com.example.contactlistback.dto.ListingDto;
import com.example.contactlistback.dto.createDto.CreateListingDto;
import com.example.contactlistback.entity.Contact;
import com.example.contactlistback.entity.Listing;
import com.example.contactlistback.entity.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Class that implements the logic so that a ListingDto can be mapped to a Listing object and vice versa, manually
 * or using Model Mapper.
 */
@Component
@RequiredArgsConstructor
public class ListingDtoConverter {

    private final ModelMapper modelMapper;

    /**
     * Converts a list of Listing to a list of ListingDto
     *
     * @param lists An arrayList with the lists to be converted to an ArrayList of ListingDto
     * @return List<ListingDto>
     */
    public List<ListingDto> convertToDtoList(List<Listing> lists) {
        return lists.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Converts a Listing to a ListingDto using Model Mapper
     *
     * @param listing The List to be converted to ListingDto
     * @return ListingDto
     */
    public ListingDto convertToDto(Listing listing) {

        ListingDto listingDto = modelMapper.map(listing, ListingDto.class);

        int userId = listing.getUser().getId();

        Set<Contact> contactList = listing.getContactList();

        Set<Integer> contactListIds = contactList.stream().map(Contact::getId).collect(Collectors.toSet());

        listingDto.setContactIds(contactListIds);
        listingDto.setUserId(userId);

        return listingDto;
    }

    /**
     * Converts an listingDto to an Listing using Model Mapper
     *
     * @param listingDto The listingDto to be converted to Listing
     * @return Listing
     */
    public Listing fromDtoToEntity(ListingDto listingDto) {
        return modelMapper.map(listingDto, Listing.class);
    }

    /**
     * Converts a listingDto to a new Listing without the Model Mapper
     *
     * @param listingDto The listingDto which contains the data input from the user
     * @param user       The User to be assigned to this listingDto
     * @return Listing
     */
    public Listing dtoToNewEntity(CreateListingDto listingDto, User user) {

        Listing listing = new Listing();
        listing.setName(listingDto.getName());
        listing.setUser(user);

        return listing;
    }
}
