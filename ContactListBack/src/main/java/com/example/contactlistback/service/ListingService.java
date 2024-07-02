package com.example.contactlistback.service;

import com.example.contactlistback.dto.createDto.CreateListingDto;
import com.example.contactlistback.entity.Listing;
import com.example.contactlistback.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ListingService {

    List<Listing> getListings(User user);

    Listing addList(CreateListingDto listingDto, User user);

    Listing editList(CreateListingDto listingDto, int listId);

    void deleteList(int id);
}
