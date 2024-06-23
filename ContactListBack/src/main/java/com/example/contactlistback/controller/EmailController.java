package com.example.contactlistback.controller;

import com.example.contactlistback.dto.EmailDto;
import com.example.contactlistback.dto.createDto.CreateEmailDto;
import com.example.contactlistback.dtoConverter.EmailDtoConverter;
import com.example.contactlistback.entity.EmailAddress;
import com.example.contactlistback.error.ApiError;
import com.example.contactlistback.error.ApiValidationError;
import com.example.contactlistback.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/emails")
@Tag(name = "EmailController", description = "Email management API")
public class EmailController {

    private final EmailService emailService;
    private final EmailDtoConverter emailDtoConverter;

    /**
     * Method for adding a new email
     *
     * @param emailDto  The new email containing the data entered by the user
     * @param idContact The ID of the contact to whom this email will be assigned to
     * @return ResponseEntity<EmailDto>
     */
    @Operation(summary = "Add a new Email", responses = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = EmailDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request: validation fails",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiValidationError.class)))
    })
    @PostMapping(path = "/add/{idContact}")
    public ResponseEntity<EmailDto> addEmail(@PathVariable int idContact,
                                             @Valid @RequestBody CreateEmailDto emailDto) {

        EmailAddress email = emailService.addEmail(emailDto, idContact);

        return new ResponseEntity<>(emailDtoConverter.convertToDto(email), HttpStatus.CREATED);
    }

    /**
     * Method for editing an existent email
     *
     * @param emailDto The EmailDto that contains the email modified by the user
     * @param id       The ID of the Email that will be edited
     * @return ResponseEntity<EmailDto>
     */
    @Operation(summary = "Edit an email by ID", responses = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = EmailDto.class))),
            @ApiResponse(responseCode = "404", description = "Email not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "400", description = "Bad request: validation fails",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiValidationError.class)))
    })
    @PutMapping(path = "/edit/{id}")
    public ResponseEntity<EmailDto> editEmail(@Valid @RequestBody CreateEmailDto emailDto, @PathVariable int id) {

        return new ResponseEntity<>(emailDtoConverter.convertToDto(emailService.editEmail(emailDto, id)), HttpStatus.OK);
    }

    /**
     * Method that deletes an Email by ID
     *
     * @param id The ID of the Email that will be deleted
     * @return ResponseEntity<?> No content
     */
    @Operation(summary = "Delete an email by ID", responses = {
            @ApiResponse(responseCode = "204", description = "No content", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = EmailDto.class))),
            @ApiResponse(responseCode = "404", description = "Email not found",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiError.class)))
    })
    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<?> deleteEmail(@PathVariable int id) {

        emailService.deleteEmail(id);

        return ResponseEntity.noContent().build();
    }

}
