package com.example.contactlistback.service.impl;

import com.example.contactlistback.dto.createDto.CreateListingDto;
import com.example.contactlistback.dtoConverter.ListingDtoConverter;
import com.example.contactlistback.entity.Listing;
import com.example.contactlistback.entity.User;
import com.example.contactlistback.exception.NotFoundException;
import com.example.contactlistback.repository.ListingRepository;
import com.example.contactlistback.repository.UserRepository;
import com.example.contactlistback.service.ListingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ListingServiceImpl implements ListingService {

    private final ListingRepository listingRepository;
    private final UserRepository userRepository;
    private final ListingDtoConverter listingDtoConverter;

    /**
     * Get the list of an authenticated user
     * @param user The authenticated user
     * @return List<Listing> An ArrayList with the listings of the user
     */
    @Override
    public List<Listing> getListings(User user) {
        return listingRepository.findAllByUser(user);
    }

    /**
     * Add a new List to an specific user
     *
     * @param listingDto The object containing the data from the user
     * @param user     The User the list will be assigned to
     * @return Listing
     * @throws NotFoundException When an User is not found
     */
    @Override
    public Listing addList(CreateListingDto listingDto, User user) {

        /*User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("User not found", userId));*/

        Listing list = listingDtoConverter.dtoToNewEntity(listingDto, user);

        return listingRepository.save(list);

    }

    /**
     * Edit an existent list by ID
     *
     * @param listingDto The object containing the new data from the user
     * @param listId     The id of the list to be edited
     * @return Listing
     * @throws NotFoundException When a Listing is not found
     */
    @Override
    public Listing editList(CreateListingDto listingDto, int listId) {

        Listing list = listingRepository.findById(listId).orElseThrow(
                () -> new NotFoundException("List not found", listId));

        list.setName(listingDto.getName());

        return listingRepository.save(list);
    }

    /**
     * Delete a list by id
     *
     * @param id The id of the list to be deleted
     * @throws NotFoundException When a list is not found
     */
    @Override
    public void deleteList(int id) {

        listingRepository.findById(id).ifPresentOrElse(
                list -> listingRepository.deleteById(id),
                () -> {
                    throw new NotFoundException("List not found", id);
                }
        );
    }
}
