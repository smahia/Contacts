package com.example.contactlistback.service;

import com.example.contactlistback.dto.createDto.CreateListingDto;
import com.example.contactlistback.entity.Contact;
import com.example.contactlistback.entity.Listing;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


@Service
public interface ListingService {

    // TODO: change the userId for the user in session?
    List<Listing> getListsByUser(int userId);

    // TODO: Does not work
    //Listing getListByUser(int userId, int listId);

    // TODO: Does not work
    //Set<Contact> getContactsByList(int listId);

    Listing addList(CreateListingDto listingDto, int userId);

    Listing editList(CreateListingDto listingDto, int listId);

    void deleteList(int id);
}
