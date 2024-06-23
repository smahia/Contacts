package com.example.contactlistback.service;

import com.example.contactlistback.dto.createDto.CreateListingDto;
import com.example.contactlistback.entity.Listing;
import org.springframework.stereotype.Service;

@Service
public interface ListingService {

    Listing addList(CreateListingDto listingDto, int userId);

    Listing editList(CreateListingDto listingDto, int listId);

    void deleteList(int id);
}
