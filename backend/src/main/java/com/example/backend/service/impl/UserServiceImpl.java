package com.example.backend.service.impl;

import com.example.backend.dto.createDto.CreateUserDto;
import com.example.backend.entity.User;
import com.example.backend.exception.DifferentPasswordException;
import com.example.backend.exception.NotFoundException;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

/**
 * Class that implements UserService
 */
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    /**
     * Method that gets all users in the database
     *
     * @return List<User>
     */
    @Override
    public List<User> getAllUsers() {

        return userRepository.findAll();
    }

    /**
     * Method that gets an User by ID
     *
     * @param id The id of the User to find
     * @return User
     * @throws NotFoundException If there is no contact in the database
     */
    @Override
    public User getUser(int id) {

        return userRepository.findById(id).orElseThrow(
                () -> new NotFoundException("User not found", id)
        );

    }

    /**
     * Find an User by username
     *
     * @param username The username to find
     * @return Optional<User>
     */
    @Override
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Method that edits an existent User
     * Hash the password
     *
     * @param editUserDto The object containing the input from the user
     * @param userId      The id of the user to be edited
     * @return User
     * @throws NotFoundException               When a User is not found
     * @throws DifferentPasswordException      When passwords don't match
     * @throws DataIntegrityViolationException When the username already exists
     */
    @Override
    public User editUser(CreateUserDto editUserDto, int userId) {

        User existentUser = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("User not found", userId));

        existentUser.setUsername(editUserDto.getUsername());

        if (editUserDto.getPassword().contentEquals(editUserDto.getPasswordConfirmation())) {

            existentUser.setPassword(passwordEncoder.encode(editUserDto.getPassword()));

        } else {

            throw new DifferentPasswordException();
        }

        try {

            return userRepository.save(existentUser);

        } catch (DataIntegrityViolationException ex) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists");
        }

    }

    /**
     * Method that deletes an User by ID
     *
     * @param id The id of the user to be deleted
     * @throws NotFoundException When a user does not exist
     */
    @Override
    public void deleteUser(int id) {

        userRepository.findById(id).ifPresentOrElse(
                user -> userRepository.deleteById(id),
                () -> {
                    throw new NotFoundException("User not found", id);
                }
        );

    }
}
