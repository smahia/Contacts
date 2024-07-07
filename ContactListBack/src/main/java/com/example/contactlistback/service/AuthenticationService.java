package com.example.contactlistback.service;

import com.example.contactlistback.dto.LoginUserDto;
import com.example.contactlistback.dto.createDto.CreateUserDto;
import com.example.contactlistback.entity.User;
import org.springframework.stereotype.Service;

/**
 * This service will contain the logic for registering a new user and authenticating an existing user.
 */
@Service
public interface AuthenticationService {

    User signUp(CreateUserDto CreateUserDto);

    User authenticate(LoginUserDto loginUserDto);
}
