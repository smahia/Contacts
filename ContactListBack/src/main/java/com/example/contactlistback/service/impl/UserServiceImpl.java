package com.example.contactlistback.service.impl;

import com.example.contactlistback.dto.createDto.CreateUserDto;
import com.example.contactlistback.dtoConverter.UserDtoConverter;
import com.example.contactlistback.entity.User;
import com.example.contactlistback.exception.NotFoundException;
import com.example.contactlistback.repository.UserRepository;
import com.example.contactlistback.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Class that implements UserService
 */
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserDtoConverter userDtoConverter;
    //private final PasswordEncoder passwordEncoder;


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
     * Method that adds a new User to the database
     *
     * @param createUserDto The object containing the input from the user
     * @return User
     */
    @Override
    public User addUser(CreateUserDto createUserDto) {

        User user = userDtoConverter.dtoToNewEntity(createUserDto);

        return userRepository.save(user);

    }

    /**
     * Method that edits an existent User
     * Hash the password
     *
     * @param editUserDto The object containing the input from the user
     * @param userId      The id of the user to be edited
     * @return User
     */
    // TODO: Hash password
    @Override
    public User editUser(CreateUserDto editUserDto, int userId) {

        User existentUser = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("User not found", userId));

        existentUser.setUsername(editUserDto.getName());
        //existentUser.setPassword(passwordEncoder.encode(editUserDto.getPassword()));
        existentUser.setPassword(editUserDto.getPassword());

        return userRepository.save(existentUser);

    }

    /**
     * Method that deletes an User by ID
     *
     * @param id The id of the user to be deleted
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
