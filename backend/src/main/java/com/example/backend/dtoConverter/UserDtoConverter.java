package com.example.backend.dtoConverter;

import com.example.backend.auth.UserRole;
import com.example.backend.dto.UserDto;
import com.example.backend.dto.createDto.CreateUserDto;
import com.example.backend.entity.Listing;
import com.example.backend.entity.User;
import com.example.backend.exception.DifferentPasswordException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Class that implements the logic so that a UserDto can be mapped to an User object and vice versa, manually
 * or using Model Mapper.
 */
@Component
@RequiredArgsConstructor
public class UserDtoConverter {

    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    /**
     * Converts a list of Users to a list of UserDto
     *
     * @param users An arrayList with the users to be converted to an ArrayList of UsersDto
     * @return List<UserDto>
     */
    public List<UserDto> convertToDtoList(List<User> users) {
        return users.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Converts a User to a UserDto using Model Mapper
     *
     * @param user The User to be converted to UserDto
     * @return UserDto
     */
    public UserDto convertToDto(User user) {

        UserDto userDto = modelMapper.map(user, UserDto.class);

        List<Listing> listing = user.getLists();

        List<Integer> listsIds = listing.stream().map(Listing::getId).toList();

        userDto.setListsIds(listsIds);

        return userDto;
    }

    /**
     * Converts a UserDto to an User using Model Mapper
     *
     * @param userDto The userDto to be converted to User
     * @return User
     */
    public User fromDtoToEntity(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

    /**
     * Converts an UserDto to a new User without the Model Mapper
     * Hash the password and set rol as USER by default
     *
     * @param userDto The userDto which contains the data input from the user
     * @return User
     * @throws DifferentPasswordException If passwords don't match
     */
    public User dtoToNewEntity(CreateUserDto userDto) {

        User user = new User();
        user.setUsername(userDto.getUsername());

        if (userDto.getPassword().contentEquals(userDto.getPasswordConfirmation())) {

            user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        } else {
            throw new DifferentPasswordException();
        }

        user.setRol(Stream.of(UserRole.USER).collect(Collectors.toSet()));

        Listing defaultList = new Listing();
        defaultList.setName("Default");
        defaultList.setUser(user);

        user.getLists().add(defaultList);

        return user;
    }
}
