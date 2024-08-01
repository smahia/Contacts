package com.example.backend.repository;

import com.example.backend.entity.Listing;
import com.example.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ListingRepository extends JpaRepository<Listing, Integer> {

    List<Listing> findAllByUser(User user);

    Optional<Listing> findByIdAndUser(int id, User user);
}
