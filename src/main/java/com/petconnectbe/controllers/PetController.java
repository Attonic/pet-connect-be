
package com.petconnectbe.controllers;

import com.petconnectbe.dto.PetDto;
import com.petconnectbe.services.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
// Rota aninhada! Agora, todos os pets estão sob o guarda-chuva de um usuário.
@RequestMapping("/api/v1/users/{userId}/pets")
@RequiredArgsConstructor
public class PetController {

    private final PetService petService;

    /**
     * Cria um novo Pet para um usuário específico.
     * O ID do usuário é pego da URL.
     */
    @PostMapping
    public ResponseEntity<PetDto> createPet(
            @PathVariable UUID userId,
            @RequestPart("pet") PetDto petDto,
            @RequestParam(value = "image", required = false) MultipartFile image) {
        // A lógica no service vai associar o pet ao userId.
        PetDto createdPet = petService.createPet(userId, petDto, image);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPet);
    }

    /**
     * Lista todos os Pets de um usuário específico.
     */
    @GetMapping
    public ResponseEntity<List<PetDto>> listAllPetsByUserId(@PathVariable UUID userId) {
        List<PetDto> pets = petService.findAllByUserId(userId);
        return ResponseEntity.ok(pets);
    }

    /**
     * Busca um Pet específico pelo seu ID, dentro do contexto de um usuário.
     */
    @GetMapping("/{petId}")
    public ResponseEntity<PetDto> findPetById(@PathVariable UUID userId, @PathVariable UUID petId) {
        return petService.findByIdAndUserId(petId, userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Atualiza um Pet específico.
     */
    @PutMapping("/{petId}")
    public ResponseEntity<PetDto> updatePet(
            @PathVariable UUID userId,
            @PathVariable UUID petId,
            @RequestPart("pet") PetDto petDto,
            @RequestParam(value = "image", required = false) MultipartFile image) {
        PetDto updatedPet = petService.updatePet(petId, userId, petDto, image);
        return ResponseEntity.ok(updatedPet);
    }

    /**
     * Deleta um Pet específico.
     */
    @DeleteMapping("/{petId}")
    public ResponseEntity<Void> deletePet(@PathVariable UUID userId, @PathVariable UUID petId) {
        petService.deleteByIdAndUserId(petId, userId);
        return ResponseEntity.noContent().build();
    }
}
