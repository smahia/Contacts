package com.example.backend.controller;

import com.example.backend.dto.LoginUserDto;
import com.example.backend.dto.UserDto;
import com.example.backend.dto.createDto.CreateUserDto;
import com.example.backend.dtoConverter.UserDtoConverter;
import com.example.backend.entity.User;
import com.example.backend.error.ApiValidationError;
import com.example.backend.response.LoginResponse;
import com.example.backend.security.jwt.JwtProvider;
import com.example.backend.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("auth/")
@Tag(name = "AuthenticationController", description = "Sign up and login")
public class AuthenticationController {

    private final JwtProvider jwtProvider;
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

    /**
     * Allow user authentication in the application
     *
     * @param loginUserDto The loginUserDto Object containing the input from the User
     * @return ResponseEntity<LoginResponse>
     */
    @Operation(summary = "Allow user authentication", responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request: validation fails",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiValidationError.class)))
    })
    @PostMapping(path = "/login")
    public ResponseEntity<LoginResponse> authenticate(@Valid @RequestBody LoginUserDto loginUserDto) {

        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtProvider.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse(jwtToken, jwtProvider.getExpirationTime());

        return ResponseEntity.ok(loginResponse);

    }
}
