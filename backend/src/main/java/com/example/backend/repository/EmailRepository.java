package com.example.backend.repository;

import com.example.backend.entity.EmailAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<EmailAddress, Integer> {
}
