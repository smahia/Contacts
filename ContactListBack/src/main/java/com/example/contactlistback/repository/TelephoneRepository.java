package com.example.contactlistback.repository;

import com.example.contactlistback.entity.Telephone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TelephoneRepository extends JpaRepository<Telephone, Integer> {
}
