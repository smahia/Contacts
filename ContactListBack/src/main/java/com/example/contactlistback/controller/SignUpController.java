package com.example.contactlistback.controller;

import com.example.contactlistback.dto.UserDto;
import com.example.contactlistback.dto.createDto.CreateUserDto;
import com.example.contactlistback.dtoConverter.UserDtoConverter;
import com.example.contactlistback.error.ApiValidationError;
import com.example.contactlistback.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class SignUpController {

    private final AuthenticationService authenticationService;
    private final UserDtoConverter userDtoConverter;

    /**
     * Add a new User to the database
     *
     * @param createUserDto The CreateUserDto Object containing the input from the User
     * @return ResponseEntity<UserDto>
     */
    @Operation(summary = "Add a new User", responses = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = CreateUserDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request: validation fails",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiValidationError.class)))
    })
    @PostMapping(path = "/signup")
    public ResponseEntity<UserDto> register(@Valid @RequestBody CreateUserDto createUserDto) {

        return new ResponseEntity<>(userDtoConverter.convertToDto(authenticationService.signUp(createUserDto)),
                HttpStatus.CREATED);
    }
}
