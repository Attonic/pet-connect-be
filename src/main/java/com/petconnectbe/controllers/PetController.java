
package com.petconnectbe.controllers;

import com.petconnectbe.dto.PetDto;
import com.petconnectbe.services.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/pets")
@RequiredArgsConstructor
public class PetController {

    private final PetService petService;

    @PostMapping
    public ResponseEntity<PetDto> createPet(
            @RequestPart("pet") PetDto petDto,
            @RequestParam(value = "image", required = false) MultipartFile image) {
        PetDto createdPet = petService.createPet(petDto, image);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPet);
    }

    @GetMapping
    public ResponseEntity<List<PetDto>> listAllPets() {
        List<PetDto> pets = petService.findAll();
        return ResponseEntity.ok(pets);
    }
}
