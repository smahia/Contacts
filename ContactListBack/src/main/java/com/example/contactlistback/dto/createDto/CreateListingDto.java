package com.example.contactlistback.dto.createDto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
public class CreateListingDto {

    @NotNull(message = "Name is mandatory")
    private String name;
}
