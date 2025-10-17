package com.petconnectbe.services.impl;

import com.petconnectbe.dto.PetCardDto;
import com.petconnectbe.models.Pet;
import com.petconnectbe.models.PetCard;
import com.petconnectbe.repositories.PetCardRepository;
import com.petconnectbe.repositories.PetRepository;
import com.petconnectbe.services.PetCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PetCardServiceImpl implements PetCardService {

    @Autowired
    private PetCardRepository petCardRepository;

    @Autowired
    private PetRepository petRepository;

    @Override
    @Transactional
    public PetCardDto createPetCard(PetCardDto petCardDto) {
        Pet pet = petRepository.findById(petCardDto.getPetId())
                .orElseThrow(() -> new RuntimeException("Pet não encontrado com o id: " + petCardDto.getPetId()));

        // Verifica se o pet já possui um cartão
        petCardRepository.findByPetId(petCardDto.getPetId()).ifPresent(pc -> {
            throw new RuntimeException("Este pet já possui um PetCard.");
        });

        PetCard petCard = new PetCard();
        petCard.setPet(pet);
        petCard.setAllergies(petCardDto.getAllergies());
        petCard.setBloodType(petCardDto.getBloodType());
        petCard.setHealthConditions(petCardDto.getHealthConditions()); // Adicionado

        PetCard savedPetCard = petCardRepository.save(petCard);

        return toDto(savedPetCard);
    }

    @Override
    @Transactional(readOnly = true)
    public PetCardDto getPetCardByPetId(Integer petId) {
        return petCardRepository.findByPetId(petId)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("PetCard não encontrado para o pet com o id: " + petId));
    }

    @Override
    @Transactional
    public PetCardDto updatePetCard(Integer id, PetCardDto petCardDto) {
        PetCard existingPetCard = petCardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("PetCard não encontrado com o id: " + id));

        // Atualiza os campos
        existingPetCard.setAllergies(petCardDto.getAllergies());
        existingPetCard.setBloodType(petCardDto.getBloodType());
        existingPetCard.setHealthConditions(petCardDto.getHealthConditions()); // Adicionado

        // Salva as alterações
        PetCard updatedPetCard = petCardRepository.save(existingPetCard);

        return toDto(updatedPetCard);
    }

    @Override
    @Transactional
    public void deletePetCard(Integer id) {
        if (!petCardRepository.existsById(id)) {
            throw new RuntimeException("PetCard não encontrado com o id: " + id);
        }
        petCardRepository.deleteById(id);
    }

    private PetCardDto toDto(PetCard petCard) {
        PetCardDto dto = new PetCardDto();
        dto.setId(petCard.getId());
        dto.setPetId(petCard.getPet().getId());
        dto.setAllergies(petCard.getAllergies());
        dto.setBloodType(petCard.getBloodType());
        dto.setHealthConditions(petCard.getHealthConditions()); // Adicionado
        return dto;
    }
}
