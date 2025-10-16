package com.petconnectbe.controllers;

import com.petconnectbe.dto.HealthCardDto;
import com.petconnectbe.services.HealthCardService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/health-cards")
public class HealthCardController {

    @Autowired
    private HealthCardService healthCardService;

    @PostMapping
    public ResponseEntity<HealthCardDto> createHealthCard(@Valid @RequestBody HealthCardDto healthCardDto) {
        HealthCardDto createdHealthCard = healthCardService.createHealthCard(healthCardDto);
        return new ResponseEntity<>(createdHealthCard, HttpStatus.CREATED);
    }

    @GetMapping("/pet/{petId}")
    public ResponseEntity<HealthCardDto> getHealthCardByPetId(@PathVariable Integer petId) {
        HealthCardDto healthCardDto = healthCardService.getHealthCardByPetId(petId);
        return ResponseEntity.ok(healthCardDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HealthCardDto> updateHealthCard(@PathVariable Integer id, @Valid @RequestBody HealthCardDto healthCardDto) {
        HealthCardDto updatedHealthCard = healthCardService.updateHealthCard(id, healthCardDto);
        return ResponseEntity.ok(updatedHealthCard);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHealthCard(@PathVariable Integer id) {
        healthCardService.deleteHealthCard(id);
        return ResponseEntity.noContent().build();
    }
}
