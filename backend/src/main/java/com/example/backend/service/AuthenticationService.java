package com.example.backend.service;

import com.example.backend.dto.LoginUserDto;
import com.example.backend.dto.createDto.CreateUserDto;
import com.example.backend.entity.User;
import org.springframework.stereotype.Service;

/**
 * This service will contain the logic for registering a new user and authenticating an existing user.
 */
@Service
public interface AuthenticationService {

    User signUp(CreateUserDto CreateUserDto);

    User authenticate(LoginUserDto loginUserDto);
}
