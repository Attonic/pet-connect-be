
package com.petconnectbe.controllers;

import com.petconnectbe.dto.VaccineDto;
import com.petconnectbe.services.VaccineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Controller para gerenciar as vacinas de um Pet específico.
 * As rotas são aninhadas para garantir que as operações de vacina
 * sempre ocorram no contexto de um Usuário e de um Pet.
 */
@RestController
@RequestMapping("/api/v1/users/{userId}/pets/{petId}/vaccines")
@RequiredArgsConstructor
public class VaccineController {

    private final VaccineService vaccineService;

    /**
     * Adiciona uma nova vacina a um pet. A validação de propriedade (se o pet pertence ao usuário)
     * será feita na camada de serviço.
     */
    @PostMapping
    public ResponseEntity<VaccineDto> createVaccine(
            @PathVariable UUID userId,
            @PathVariable UUID petId,
            @RequestBody VaccineDto vaccineDto) {
        // A camada de serviço vai usar userId e petId para validar e associar a vacina.
        VaccineDto createdVaccine = vaccineService.createVaccine(userId, petId, vaccineDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdVaccine);
    }

    /**
     * Lista todas as vacinas de um pet específico.
     */
    @GetMapping
    public ResponseEntity<List<VaccineDto>> listAllVaccinesForPet(
            @PathVariable UUID userId,
            @PathVariable UUID petId) {
        List<VaccineDto> vaccines = vaccineService.findAllByPetId(userId, petId);
        return ResponseEntity.ok(vaccines);
    }

    /**
     * Busca uma vacina específica pelo seu ID.
     */
    @GetMapping("/{vaccineId}")
    public ResponseEntity<VaccineDto> findVaccineById(
            @PathVariable UUID userId,
            @PathVariable UUID petId,
            @PathVariable UUID vaccineId) {
        return vaccineService.findVaccineById(userId, petId, vaccineId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Atualiza uma vacina existente.
     */
    @PutMapping("/{vaccineId}")
    public ResponseEntity<VaccineDto> updateVaccine(
            @PathVariable UUID userId,
            @PathVariable UUID petId,
            @PathVariable UUID vaccineId,
            @RequestBody VaccineDto vaccineDto) {
        VaccineDto updatedVaccine = vaccineService.updateVaccine(userId, petId, vaccineId, vaccineDto);
        return ResponseEntity.ok(updatedVaccine);
    }

    /**
     * Deleta uma vacina específica.
     */
    @DeleteMapping("/{vaccineId}")
    public ResponseEntity<Void> deleteVaccine(
            @PathVariable UUID userId,
            @PathVariable UUID petId,
            @PathVariable UUID vaccineId) {
        vaccineService.deleteVaccine(userId, petId, vaccineId);
        return ResponseEntity.noContent().build();
    }
}
