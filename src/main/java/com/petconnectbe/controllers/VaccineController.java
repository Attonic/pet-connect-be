package com.petconnectbe.controllers;

import com.petconnectbe.dto.VaccineDto;
import com.petconnectbe.services.VaccineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/vaccines")
@RequiredArgsConstructor
public class VaccineController {

    private final VaccineService vaccineService;

    /**
     * Endpoint para criar um novo registro de vacina para um PetCard específico.
     * @param petCardId o ID do PetCard ao qual a vacina será associada.
     * @param vaccineDto os dados da vacina a ser criada.
     * @return a vacina recém-criada com status 201 (Created).
     */
    @PostMapping("/pet-card/{petCardId}")
    public ResponseEntity<VaccineDto> createVaccine(@PathVariable UUID petCardId, @RequestBody VaccineDto vaccineDto) {
        VaccineDto createdVaccine = vaccineService.createVaccine(petCardId, vaccineDto);
        return new ResponseEntity<>(createdVaccine, HttpStatus.CREATED);
    }
}
