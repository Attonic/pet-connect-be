package com.petconnectbe.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * DTO for the HealthCard entity. Used to transfer HealthCard data between the frontend and the backend.
 */
@Data
public class HealthCardDto {

    private Integer id;

    /**
     * The ID of the Pet to which this health card is associated.
     * This field is essential to link the HealthCard to the correct Pet when creating or updating.
     */
    @NotNull(message = "The pet ID is mandatory.")
    private Integer petId;

    /**
     * Known allergies of the Pet.
     */
    private String allergies;

    /**
     * The blood type of the Pet.
     */
    private String bloodType;
}
