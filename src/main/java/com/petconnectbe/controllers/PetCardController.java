package com.petconnectbe.controllers;

import com.petconnectbe.dto.PetCardDto;
import com.petconnectbe.services.PetCardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users/{userId}/pets/{petId}/pet-card")
@RequiredArgsConstructor
public class PetCardController {

    private final PetCardService petCardService;

    private void validateUser(Jwt jwt, UUID userId) {
        UUID authenticatedUserId = UUID.fromString(jwt.getSubject());
        if (!authenticatedUserId.equals(userId)) {
            throw new SecurityException("Access Denied: User can only access their own data.");
        }
    }

    @GetMapping
    public ResponseEntity<PetCardDto> getPetCardByPetId(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID userId,
            @PathVariable UUID petId) {
        validateUser(jwt, userId);
        PetCardDto petCardDto = petCardService.getPetCardByPetId(petId);
        return ResponseEntity.ok(petCardDto);
    }

    @PutMapping
    public ResponseEntity<PetCardDto> updatePetCard(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID userId,
            @PathVariable UUID petId,
            @Valid @RequestBody PetCardDto petCardDto) {
        validateUser(jwt, userId);
        // The service needs to validate that the petId matches the user.
        // We will add this logic to the service layer.
        PetCardDto updatedPetCard = petCardService.updatePetCardByPetId(petId, petCardDto);
        return ResponseEntity.ok(updatedPetCard);
    }
}
