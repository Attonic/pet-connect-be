package com.petconnectbe.services.impl;

import com.petconnectbe.dto.HealthCardDto;
import com.petconnectbe.models.Pet;
import com.petconnectbe.models.HealthCard;
import com.petconnectbe.repositories.HealthCardRepository;
import com.petconnectbe.repositories.PetRepository;
import com.petconnectbe.services.HealthCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HealthCardServiceImpl implements HealthCardService {

    @Autowired
    private HealthCardRepository healthCardRepository;

    @Autowired
    private PetRepository petRepository;

    @Override
    @Transactional
    public HealthCardDto createHealthCard(HealthCardDto healthCardDto) {
        Pet pet = petRepository.findById(healthCardDto.getPetId())
                .orElseThrow(() -> new RuntimeException("Pet not found with id: " + healthCardDto.getPetId()));

        // Check if the pet already has a health card
        healthCardRepository.findByPetId(healthCardDto.getPetId()).ifPresent(hc -> {
            throw new RuntimeException("This pet already has a HealthCard.");
        });

        HealthCard healthCard = new HealthCard();
        healthCard.setPet(pet);
        healthCard.setAllergies(healthCardDto.getAllergies());
        healthCard.setBloodType(healthCardDto.getBloodType());

        HealthCard savedHealthCard = healthCardRepository.save(healthCard);

        return toDto(savedHealthCard);
    }

    @Override
    @Transactional(readOnly = true)
    public HealthCardDto getHealthCardByPetId(Integer petId) {
        return healthCardRepository.findByPetId(petId)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("HealthCard not found for pet with id: " + petId));
    }

    @Override
    @Transactional
    public HealthCardDto updateHealthCard(Integer id, HealthCardDto healthCardDto) {
        HealthCard existingHealthCard = healthCardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("HealthCard not found with id: " + id));

        // Update fields
        existingHealthCard.setAllergies(healthCardDto.getAllergies());
        existingHealthCard.setBloodType(healthCardDto.getBloodType());

        // Save changes
        HealthCard updatedHealthCard = healthCardRepository.save(existingHealthCard);

        return toDto(updatedHealthCard);
    }

    @Override
    @Transactional
    public void deleteHealthCard(Integer id) {
        if (!healthCardRepository.existsById(id)) {
            throw new RuntimeException("HealthCard not found with id: " + id);
        }
        healthCardRepository.deleteById(id);
    }

    private HealthCardDto toDto(HealthCard healthCard) {
        HealthCardDto dto = new HealthCardDto();
        dto.setId(healthCard.getId());
        dto.setPetId(healthCard.getPet().getId());
        dto.setAllergies(healthCard.getAllergies());
        dto.setBloodType(healthCard.getBloodType());
        return dto;
    }
}
