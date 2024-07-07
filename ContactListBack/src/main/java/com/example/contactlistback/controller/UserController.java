package com.example.contactlistback.controller;

import com.example.contactlistback.dto.UserDto;
import com.example.contactlistback.dto.createDto.CreateUserDto;
import com.example.contactlistback.dtoConverter.UserDtoConverter;
import com.example.contactlistback.entity.User;
import com.example.contactlistback.error.ApiValidationError;
import com.example.contactlistback.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
@Tag(name = "UserController", description = "User management API for user registration and authentication")
public class UserController {

    UserService userService;
    UserDtoConverter userDtoConverter;

    /**
     * Return all the users in the database
     *
     * @return ResponseEntity<List < UserDto>> A UserDto list
     */
    @Operation(summary = "Get all users", responses = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class)))
    })
    @GetMapping(path = "/getUsers")
    public ResponseEntity<List<UserDto>> getUsers() {

        return new ResponseEntity<>(userDtoConverter.convertToDtoList(userService.getAllUsers()), HttpStatus.OK);
    }

    /**
     * Return an User by ID
     *
     * @param id The id of the User to be found
     * @return ResponseEntity<UserDto>
     */
    @Operation(summary = "Get an user by ID", responses = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping(path = "/getUser/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable int id) {

        return new ResponseEntity<>(userDtoConverter.convertToDto(userService.getUser(id)), HttpStatus.OK);
    }

    /**
     * Edit the data of an existent User by id
     *
     * @param createUserDto The CreateUserDto with the new data from the user
     * @param id            The id of the User to be edited
     * @return ResponseEntity<UserDto>
     */
    @Operation(summary = "Edit an user by ID", responses = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "400", description = "Bad request: validation fails",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiValidationError.class)))
    })
    @PutMapping(path = "edit/{id}")
    public ResponseEntity<UserDto> editUser(@Valid @RequestBody CreateUserDto createUserDto, @PathVariable int id) {

        return new ResponseEntity<>(userDtoConverter.convertToDto(userService.editUser(createUserDto, id)), HttpStatus.OK);
    }

    /**
     * Delete an User by ID
     *
     * @param id The id of the user to be deleted
     * @return ResponseEntity<?> No Content
     */
    @Operation(summary = "Delete an user by ID", responses = {
            @ApiResponse(responseCode = "204", description = "No content", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping(path = "delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id) {

        userService.deleteUser(id);

        return ResponseEntity.noContent().build();
    }

    /**
     * Get the details of the authenticated user
     *
     * @param user The authenticated user
     * @return UserDto
     */
    @Operation(summary = "Get the details of the authenticated user", responses = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping(path = "/me")
    public UserDto getAuthenticatedUser(@AuthenticationPrincipal User user) {
        return userDtoConverter.convertToDto(user);
    }
}
