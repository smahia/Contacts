package com.example.contactlistback.controller;

import com.example.contactlistback.dto.TelephoneDto;
import com.example.contactlistback.entity.Contact;
import com.example.contactlistback.entity.Telephone;
import com.example.contactlistback.service.TelephoneService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/telephones")
public class TelephoneController {

    private final TelephoneService telephoneService;

    /**
     * Method for adding a new Telephone
     */
    @Operation(summary = "Add a new telephone")
    @PostMapping(path = "{idContact}/add")
    public ResponseEntity<?> addTelephone(@PathVariable int idContact,
            @RequestBody TelephoneDto telephoneDto) {

        return telephoneService.addTelephone(telephoneDto, idContact);
    }


    /**
     * Method for editing a Telephone
     */
    @Operation(summary = "Edit a telephone by ID", responses = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Telephone.class))),
            @ApiResponse(responseCode = "404", description = "Telephone not found")
    })
    @PutMapping(path = "edit/{idTelephone}")
    public ResponseEntity<?> editTelephone(@RequestBody TelephoneDto telephoneDtoToEdit,
                                           @PathVariable int idTelephone) {

        return telephoneService.editTelephone(telephoneDtoToEdit, idTelephone);
    }

    /**
     * Method for deleting a Telephone
     */
    @Operation(summary = "Delete a telephone by ID", responses = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Telephone.class))),
            @ApiResponse(responseCode = "404", description = "Telephone not found")
    })
    @DeleteMapping(path = "delete/{idTelephone}")
    public ResponseEntity<?> deleteTelephone(@PathVariable int idTelephone) {

        return telephoneService.deleteTelephone(idTelephone);
    }

}
