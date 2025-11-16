package com.petconnectbe.services.impl;

import com.petconnectbe.dto.PetCardDto;
import com.petconnectbe.models.Pet;
import com.petconnectbe.models.PetCard;
import com.petconnectbe.repositories.PetCardRepository;
import com.petconnectbe.services.PetCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PetCardServiceImpl implements PetCardService {

    private final PetCardRepository petCardRepository;

    @Override
    @Transactional
    public PetCardDto createPetCardForPet(Pet pet) {
        if (petCardRepository.findByPetId(pet.getId()).isPresent()) {
            throw new IllegalStateException("PetCard already exists for this pet.");
        }
        PetCard petCard = new PetCard();
        petCard.setPet(pet);
        PetCard savedPetCard = petCardRepository.save(petCard);
        return toDto(savedPetCard);
    }

    @Override
    @Transactional(readOnly = true)
    public PetCardDto getPetCardByPetId(UUID petId) {
        return petCardRepository.findByPetId(petId)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("PetCard not found for pet with id: " + petId));
    }

    @Override
    @Transactional
    public PetCardDto updatePetCardByPetId(UUID petId, PetCardDto petCardDto) {
        PetCard existingPetCard = petCardRepository.findByPetId(petId)
                .orElseThrow(() -> new RuntimeException("PetCard not found for pet with id: " + petId));

        // Atualmente, não há campos editáveis no PetCard, mas a estrutura está pronta.
        // Ex: existingPetCard.setSomeField(petCardDto.getSomeField());

        PetCard updatedPetCard = petCardRepository.save(existingPetCard);
        return toDto(updatedPetCard);
    }

    private PetCardDto toDto(PetCard petCard) {
        PetCardDto dto = new PetCardDto();
        dto.setId(petCard.getId());
        dto.setPetId(petCard.getPet().getId());
        return dto;
    }
}
