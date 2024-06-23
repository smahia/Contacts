package com.example.contactlistback.repository;

import com.example.contactlistback.entity.Listing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListingRepository extends JpaRepository<Listing, Integer> {
}
