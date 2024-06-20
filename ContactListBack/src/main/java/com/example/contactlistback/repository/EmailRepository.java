package com.example.contactlistback.repository;

import com.example.contactlistback.entity.EmailAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<EmailAddress, Integer> {
}
