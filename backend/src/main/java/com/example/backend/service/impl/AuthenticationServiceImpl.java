package com.example.backend.service.impl;

import com.example.backend.dto.LoginUserDto;
import com.example.backend.dto.createDto.CreateUserDto;
import com.example.backend.dtoConverter.UserDtoConverter;
import com.example.backend.entity.User;
import com.example.backend.exception.GenericException;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final UserDtoConverter userDtoConverter;
    private final AuthenticationManager authenticationManager;

    /**
     * Method that adds a new User to the database
     *
     * @param createUserDto The object containing the input from the user
     * @return User
     * @throws DataIntegrityViolationException If the username is not unique
     */
    @Override
    public User signUp(CreateUserDto createUserDto) {

        User user = userDtoConverter.dtoToNewEntity(createUserDto);

        try {

            return userRepository.save(user);

        } catch (DataIntegrityViolationException ex) {
            throw new GenericException("Username already exists");
        }
    }

    /**
     * Allow user authentication in the application
     *
     * @param loginUserDto The object containing the input from the user
     * @return User
     * @throws UsernameNotFoundException If the user does not exist
     */
    @Override
    public User authenticate(LoginUserDto loginUserDto) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUserDto.getUsername(),
                        loginUserDto.getPassword()
                )
        );

        return userRepository.findByUsername(loginUserDto.getUsername()).orElseThrow(
                () -> new UsernameNotFoundException(loginUserDto.getUsername() + "not found")
        );

    }
}
