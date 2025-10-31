package com.petconnectbe.services.impl;

import com.petconnectbe.dto.PetDto;
import com.petconnectbe.mappers.PetMapper;
import com.petconnectbe.models.Pet;
import com.petconnectbe.models.User;
import com.petconnectbe.repositories.PetRepository;
import com.petconnectbe.repositories.UserRepository;
import com.petconnectbe.services.FileStorageService;
import com.petconnectbe.services.PetService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;
    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;
    private final PetMapper petMapper;

    @Override
    @Transactional
    public PetDto createPet(PetDto petDto, MultipartFile image) {
        User tutor = userRepository.findById(petDto.getTutorId())
            .orElseThrow(() -> new EntityNotFoundException("Tutor não encontrado com o ID: " + petDto.getTutorId()));

        Pet pet = petMapper.toEntity(petDto);
        pet.setTutor(tutor);

        if (image != null && !image.isEmpty()) {
            String imageUrl = fileStorageService.store(image);
            pet.setImageUrl(imageUrl);
        }

        Pet savedPet = petRepository.save(pet);
        return petMapper.toDto(savedPet);
    }

    @Override
    @Transactional
    public PetDto updatePetImage(UUID id, MultipartFile image) {
        Pet pet = petRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Pet não encontrado com o ID: " + id));

        if (image != null && !image.isEmpty()) {
            String imageUrl = fileStorageService.store(image);
            pet.setImageUrl(imageUrl);
        }

        Pet updatedPet = petRepository.save(pet);
        return petMapper.toDto(updatedPet);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PetDto> findById(UUID id) {
        return petRepository.findById(id).map(petMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PetDto> findAll() {
        return petRepository.findAll().stream()
                .map(petMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PetDto update(UUID id, PetDto petDto) {
        Pet existingPet = petRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pet não encontrado com o ID: " + id));

        Pet updatedData = petMapper.toEntity(petDto);
        updatedData.setId(existingPet.getId());
        updatedData.setTutor(existingPet.getTutor());

        Pet savedPet = petRepository.save(updatedData);
        return petMapper.toDto(savedPet);
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        if (!petRepository.existsById(id)) {
            throw new EntityNotFoundException("Pet não encontrado com o ID: " + id);
        }
        petRepository.deleteById(id);
    }
}
