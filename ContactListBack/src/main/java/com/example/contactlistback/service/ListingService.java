package com.example.contactlistback.service;

import com.example.contactlistback.dto.createDto.CreateListingDto;
import com.example.contactlistback.entity.Listing;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface ListingService {

    // TODO: change the userId for the user in session?
    List<Listing> getListsByUser(int userId);

    Listing getListByUser(int userId, int listId);

    Listing addList(CreateListingDto listingDto, int userId);

    Listing editList(CreateListingDto listingDto, int listId);

    void deleteList(int id);
}
