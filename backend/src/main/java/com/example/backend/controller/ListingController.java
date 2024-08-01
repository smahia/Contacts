package com.example.backend.controller;

import com.example.backend.dto.ListingDto;
import com.example.backend.dto.createDto.CreateListingDto;
import com.example.backend.dtoConverter.ListingDtoConverter;
import com.example.backend.entity.User;
import com.example.backend.error.ApiError;
import com.example.backend.error.ApiValidationError;
import com.example.backend.service.ListingService;
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
@RequestMapping("/listings")
@Tag(name = "ListingController", description = "Listing management API")
public class ListingController {

    // TODO: get user in session and not by id?

    private final ListingService listingService;
    private final ListingDtoConverter listingDtoConverter;

    @GetMapping(path = "/getListings")
    public ResponseEntity<List<ListingDto>> getListings(@AuthenticationPrincipal User user) {

        /*if (user.getRol().contains(UserRole.USER)) {

            return new ResponseEntity<>(listingDtoConverter.convertToDtoList(listingService.getListings(user)),
                    HttpStatus.OK);

        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }*/

        return new ResponseEntity<>(listingDtoConverter.convertToDtoList(listingService.getListings(user)),
                HttpStatus.OK);

    }

    /**
     * Get a list by ID
     *
     * @param id The id of the list
     * @return ResponseEntity<ListingDto>
     */
    @Operation(summary = "Get a list by ID", responses = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ListingDto.class))),
            @ApiResponse(responseCode = "404", description = "List not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping(path = "/getList/{id}")
    public ResponseEntity<ListingDto> getList(@PathVariable int id, @AuthenticationPrincipal User user) {

        return new ResponseEntity<>(listingDtoConverter.convertToDto(listingService.getListById(id, user)), HttpStatus.OK);
    }

    /**
     * Add a list to an user
     *
     * @param listingDto The object containing the input from the user
     * @param user       The user to whom the list will be assigned
     * @return ResponseEntity<ListingDto>
     */
    @Operation(summary = "Add a new list to an user", responses = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ListingDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request: validation fails",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiValidationError.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)))
    })
    @PostMapping(path = "/add/")
    public ResponseEntity<ListingDto> addList(@Valid @RequestBody CreateListingDto listingDto,
                                              @AuthenticationPrincipal User user) {

        return new ResponseEntity<>(listingDtoConverter.convertToDto(listingService.addList(listingDto, user)),
                HttpStatus.CREATED);
    }

    /**
     * Edit an existent list
     *
     * @param idList         The id of the list that will be edited
     * @param editListingDto The object containing the input from the user
     * @return ResponseEntity<ListingDto>
     */
    @Operation(summary = "Edit a list by ID", responses = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ListingDto.class))),
            @ApiResponse(responseCode = "404", description = "List not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "400", description = "Bad request: validation fails",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiValidationError.class)))
    })
    @PutMapping(path = "edit/{idList}")
    public ResponseEntity<ListingDto> editList(@PathVariable int idList,
                                               @Valid @RequestBody CreateListingDto editListingDto) {

        return new ResponseEntity<>(listingDtoConverter.convertToDto(
                listingService.editList(editListingDto, idList)), HttpStatus.OK);
    }

    /**
     * Delete an existent list
     *
     * @param idList The id of the list that will be deleted
     * @return ResponseEntity<?> No content
     */
    @Operation(summary = "Delete a list by ID", responses = {
            @ApiResponse(responseCode = "204", description = "No content", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ListingDto.class))),
            @ApiResponse(responseCode = "404", description = "List not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)))
    })
    @DeleteMapping(path = "delete/{idList}")
    public ResponseEntity<?> deleteList(@PathVariable int idList) {

        listingService.deleteList(idList);

        return ResponseEntity.noContent().build();
    }
}
