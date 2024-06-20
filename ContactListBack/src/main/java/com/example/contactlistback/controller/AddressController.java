package com.example.contactlistback.controller;

import com.example.contactlistback.dto.AddressDto;
import com.example.contactlistback.dtoConverter.AddressDtoConverter;
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
     * @param idContact The ID of the contact to whom the address will be assigned to
     * @param addressDto The object containing the input from the user
     * @return ResponseEntity<AddressDto>
     */
    @Operation(summary = "Add a new Address", responses = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = AddressDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request: validation fails",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AddressDto.class)))
    })
    @PostMapping(path = "/add/{idContact}")
    public ResponseEntity<AddressDto> addAddress(@PathVariable int idContact, @Valid @RequestBody AddressDto addressDto) {


        return new ResponseEntity<>(addressDtoConverter.convertToDto(addressService.addAddress(addressDto, idContact)), HttpStatus.CREATED);
    }

}
