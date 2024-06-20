package com.example.contactlistback.controller;

import com.example.contactlistback.dto.EmailDto;
import com.example.contactlistback.dto.TelephoneDto;
import com.example.contactlistback.dtoConverter.TelephoneDtoConverter;
import com.example.contactlistback.entity.Telephone;
import com.example.contactlistback.service.TelephoneService;
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
@RequestMapping("/telephones")
@Tag(name = "TelephoneController", description = "Telephone management API")
public class TelephoneController {

    private final TelephoneService telephoneService;
    private final TelephoneDtoConverter telephoneDtoConverter;

    /**
     * Method for adding a new Telephone
     * @param telephoneDto The TelephoneDTO object containing the data entered by the user
     * @param idContact The id of the Contact to which the phone will be assigned
     * @return ResponseEntity<TelephoneDto>
     */
    @Operation(summary = "Add a new Telephone", responses = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = TelephoneDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request: validation fails",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TelephoneDto.class)))
    })
    @PostMapping(path = "/{idContact}/add")
    public ResponseEntity<TelephoneDto> addTelephone(@PathVariable int idContact,
                                                     @Valid @RequestBody TelephoneDto telephoneDto) {

        Telephone telephone = telephoneService.addTelephone(telephoneDto, idContact);

        return new ResponseEntity<>(telephoneDtoConverter.convertToDto(telephone), HttpStatus.CREATED);
    }


    /**
     * Method for editing a Telephone by id
     * @param telephoneDtoToEdit The TelephoneDto existent in the database and that contains the new data
     * entered by the user
     * @param idTelephone The id of the Telephone to be edited
     * @return ResponseEntity<TelephoneDto>
     */
    @Operation(summary = "Edit a telephone by ID", responses = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = TelephoneDto.class))),
            @ApiResponse(responseCode = "404", description = "Telephone not found"),
            @ApiResponse(responseCode = "400", description = "Bad request: validation fails",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TelephoneDto.class)))
    })
    @PutMapping(path = "/edit/{idTelephone}")
    public ResponseEntity<TelephoneDto> editTelephone(@Valid @RequestBody TelephoneDto telephoneDtoToEdit,
                                           @PathVariable int idTelephone) {

        Telephone telephone = telephoneService.editTelephone(telephoneDtoToEdit, idTelephone);

        return new ResponseEntity<>(telephoneDtoConverter.convertToDto(telephone), HttpStatus.OK);

    }

    /**
     * Method for deleting a Telephone by id
     * @param idTelephone The id of the telephone to be deleted
     * @return ResponseEntity<?> No content
     */
    @Operation(summary = "Delete a telephone by ID", responses = {
            @ApiResponse(responseCode = "204", description = "No content", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = TelephoneDto.class))),
            @ApiResponse(responseCode = "404", description = "Telephone not found")
    })
    @DeleteMapping(path = "/delete/{idTelephone}")
    public ResponseEntity<?> deleteTelephone(@PathVariable int idTelephone) {

        telephoneService.deleteTelephone(idTelephone);

        return ResponseEntity.noContent().build();
    }

}
