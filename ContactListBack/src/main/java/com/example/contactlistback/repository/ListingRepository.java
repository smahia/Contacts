package com.example.contactlistback.repository;

import com.example.contactlistback.entity.Listing;
import com.example.contactlistback.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ListingRepository extends JpaRepository<Listing, Integer> {

    List<Listing> findAllByUser(User user);

    // TODO: CHECK THIS, RETURNS MORE THAN ONE RESULT
    Listing findByUser(User user);
}
