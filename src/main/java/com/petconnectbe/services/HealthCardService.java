package com.petconnectbe.services;

import com.petconnectbe.dto.HealthCardDto;

public interface HealthCardService {

    /**
     * Creates a new health card for a pet.
     * @param healthCardDto The DTO containing the information of the new HealthCard.
     * @return The DTO of the created HealthCard.
     */
    HealthCardDto createHealthCard(HealthCardDto healthCardDto);

    /**
     * Retrieves the health card associated with a specific pet.
     * @param petId The ID of the pet.
     * @return The DTO of the found HealthCard.
     */
    HealthCardDto getHealthCardByPetId(Integer petId);

    /**
     * Updates the information of an existing health card.
     * @param id The ID of the HealthCard to be updated.
     * @param healthCardDto The DTO with the new information.
     * @return The DTO of the updated HealthCard.
     */
    HealthCardDto updateHealthCard(Integer id, HealthCardDto healthCardDto);

    /**
     * Deletes a health card by its ID.
     * @param id The ID of the HealthCard to be deleted.
     */
    void deleteHealthCard(Integer id);
}
