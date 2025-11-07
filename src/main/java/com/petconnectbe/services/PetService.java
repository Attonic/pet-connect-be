package com.petconnectbe.services;

import com.petconnectbe.dto.PetDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface PetService {
    PetDto createPet(PetDto petDto, MultipartFile image);

    PetDto updatePetImage(Integer id, MultipartFile image);

    Optional<PetDto> findById(Integer id);

    List<PetDto> findAll();

    PetDto update(Integer id, PetDto petDto);

    void deleteById(Integer id);
}
