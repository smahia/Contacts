package com.example.backend.service;

import com.example.backend.dto.createDto.CreateListingDto;
import com.example.backend.entity.Listing;
import com.example.backend.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ListingService {

    List<Listing> getListings(User user);

    Listing getListById(int id, User user);

    Listing addList(CreateListingDto listingDto, User user);

    Listing editList(CreateListingDto listingDto, int listId);

    void deleteList(int id);
}
