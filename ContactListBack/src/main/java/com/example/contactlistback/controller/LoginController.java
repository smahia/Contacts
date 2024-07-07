package com.example.contactlistback.controller;

import com.example.contactlistback.dto.LoginUserDto;
import com.example.contactlistback.entity.User;
import com.example.contactlistback.error.ApiValidationError;
import com.example.contactlistback.response.LoginResponse;
import com.example.contactlistback.security.jwt.JwtProvider;
import com.example.contactlistback.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class LoginController {

    private final JwtProvider jwtProvider;
    private final AuthenticationService authenticationService;

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
