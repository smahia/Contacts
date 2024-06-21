package com.example.contactlistback.controller;

import com.example.contactlistback.dto.AddressDto;
import com.example.contactlistback.dto.createDto.CreateAddressDto;
import com.example.contactlistback.dtoConverter.AddressDtoConverter;
import com.example.contactlistback.error.ApiError;
import com.example.contactlistback.error.ApiValidationError;
import com.example.contactlistback.service.AddressService;
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

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/address")
@Tag(name = "AddressController", description = "Address management API")
public class AddressController {

    private final AddressService addressService;
    private final AddressDtoConverter addressDtoConverter;

    /**
     * Add a new address
     *
     * @param idContact  The ID of the contact to whom the address will be assigned to
     * @param addressDto The object containing the input from the user
     * @return ResponseEntity<AddressDto>
     */
    @Operation(summary = "Add a new Address", responses = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = AddressDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request: validation fails",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiValidationError.class)))
    })
    @PostMapping(path = "/add/{idContact}")
    public ResponseEntity<AddressDto> addAddress(@PathVariable int idContact,
                                                 @Valid @RequestBody CreateAddressDto addressDto) {


        return new ResponseEntity<>(addressDtoConverter.convertToDto(
                addressService.addAddress(addressDto, idContact)), HttpStatus.CREATED);
    }

    /**
     * Endpoint that edits an Address by ID
     *
     * @param id               The id of the address to be edited
     * @param addressDtoedited The object with the data entered by the user
     * @return ResponseEntity<AddressDto>
     */
    @Operation(summary = "Edit an existent Address", responses = {
            @ApiResponse(responseCode = "200", description = "Sucess", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = AddressDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request: validation fails",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiValidationError.class))),
            @ApiResponse(responseCode = "404", description = "Address not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)))
    })
    @PutMapping(path = "edit/{id}")
    public ResponseEntity<AddressDto> editAddress(@PathVariable int id,
                                                  @Valid @RequestBody CreateAddressDto addressDtoedited) {

        return new ResponseEntity<>(
                addressDtoConverter.convertToDto(addressService.editAddress(addressDtoedited, id)),
                HttpStatus.OK
        );

    }

    /**
     * Delete an Address by ID
     *
     * @param id The id of the address to be deleted
     * @return ResponseEntity<?> No content
     */
    @Operation(summary = "Delete an Address", responses = {
            @ApiResponse(responseCode = "204", description = "Not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AddressDto.class))),
            @ApiResponse(responseCode = "404", description = "Address not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)))
    })
    @DeleteMapping(path = "delete/{id}")
    public ResponseEntity<?> deleteAddress(@PathVariable int id) {
        addressService.deleteAddress(id);

        return ResponseEntity.noContent().build();
    }

}
