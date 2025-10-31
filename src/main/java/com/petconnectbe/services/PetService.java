package com.petconnectbe.services;

import com.petconnectbe.dto.PetDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PetService {
    PetDto createPet(PetDto petDto, MultipartFile image);

    PetDto updatePetImage(UUID id, MultipartFile image);

    Optional<PetDto> findById(UUID id);

    List<PetDto> findAll();

    PetDto update(UUID id, PetDto petDto);

    void deleteById(UUID id);
}
