package com.example.contactlistback.controller;

import com.example.contactlistback.dto.TelephoneDto;
import com.example.contactlistback.service.TelephoneService;
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
    @PostMapping(path = "{idContact}/add")
    public ResponseEntity<?> addTelephone(@PathVariable int idContact,
            @RequestBody TelephoneDto telephoneDto) {

        return telephoneService.addTelephone(telephoneDto, idContact);
    }


    /**
     * Method for editing a Telephone
     */
    @PutMapping(path = "edit/{idTelephone}")
    public ResponseEntity<?> editTelephone(@RequestBody TelephoneDto telephoneDtoToEdit,
                                           @PathVariable int idTelephone) {

        return telephoneService.editTelephone(telephoneDtoToEdit, idTelephone);
    }

    /**
     * Method for deleting a Telephone
     */
    @DeleteMapping(path = "delete/{idTelephone}")
    public ResponseEntity<?> deleteTelephone(@PathVariable int idTelephone) {

        return telephoneService.deleteTelephone(idTelephone);
    }

}
