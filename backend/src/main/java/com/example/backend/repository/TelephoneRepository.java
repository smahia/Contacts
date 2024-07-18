package com.example.backend.repository;

import com.example.backend.entity.Telephone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TelephoneRepository extends JpaRepository<Telephone, Integer> {
}
