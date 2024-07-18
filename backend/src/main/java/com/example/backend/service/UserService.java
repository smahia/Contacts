package com.example.backend.service;

import com.example.backend.dto.createDto.CreateUserDto;
import com.example.backend.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Class that implements the header methods to handle CRUD operations to manage user data.
 */
@Service
public interface UserService {

    List<User> getAllUsers();

    User getUser(int id);

    Optional<User> findUserByUsername(String username);

    User editUser(CreateUserDto editUserDto, int userId);

    void deleteUser(int id);
}
