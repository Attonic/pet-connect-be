package com.petconnectbe.controllers;

import com.petconnectbe.dto.PetCardDto;
import com.petconnectbe.services.PetCardService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/pet-cards")
public class PetCardController {

    @Autowired
    private PetCardService petCardService;

    @PostMapping
    public ResponseEntity<PetCardDto> createPetCard(@Valid @RequestBody PetCardDto petCardDto) {
        PetCardDto createdPetCard = petCardService.createPetCard(petCardDto);
        return new ResponseEntity<>(createdPetCard, HttpStatus.CREATED);
    }

    @GetMapping("/pet/{petId}")
    public ResponseEntity<PetCardDto> getPetCardByPetId(@PathVariable UUID petId) {
        PetCardDto petCardDto = petCardService.getPetCardByPetId(petId);
        return ResponseEntity.ok(petCardDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PetCardDto> updatePetCard(@PathVariable UUID id, @Valid @RequestBody PetCardDto petCardDto) {
        PetCardDto updatedPetCard = petCardService.updatePetCard(id, petCardDto);
        return ResponseEntity.ok(updatedPetCard);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePetCard(@PathVariable UUID id) {
        petCardService.deletePetCard(id);
        return ResponseEntity.noContent().build();
    }
}
