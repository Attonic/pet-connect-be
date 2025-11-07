package com.petconnectbe.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petconnectbe.dto.PetDto;
import com.petconnectbe.services.PetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/pets")
public class PetController {

    private final PetService petService;
    private final ObjectMapper objectMapper;

    public PetController(PetService petService, ObjectMapper objectMapper) {
        this.petService = petService;
        this.objectMapper = objectMapper;
    }

    @PostMapping(consumes = { "multipart/form-data" })
    public ResponseEntity<PetDto> createPet(
            @RequestPart("pet") String petJson,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        try {
            PetDto petDto = objectMapper.readValue(petJson, PetDto.class);
            PetDto createdPet = petService.createPet(petDto, image);
            return new ResponseEntity<>(createdPet, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{id}/image")
    public ResponseEntity<PetDto> updatePetImage(
            @PathVariable Integer id,
            @RequestParam("image") MultipartFile image) {
        PetDto updatedPet = petService.updatePetImage(id, image);
        return ResponseEntity.ok(updatedPet);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetDto> findPetById(@PathVariable Integer id) {
        return petService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<PetDto>> findAllPets() {
        List<PetDto> pets = petService.findAll();
        return ResponseEntity.ok(pets);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PetDto> updatePet(@PathVariable Integer id, @RequestBody PetDto petDto) {
        PetDto updatedPet = petService.update(id, petDto);
        return ResponseEntity.ok(updatedPet);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable Integer id) {
        petService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
