package com.petconnectbe.services.impl;

import com.petconnectbe.dto.PetDto;
import com.petconnectbe.models.Pet;
import com.petconnectbe.models.User;
import com.petconnectbe.repositories.PetRepository;
import com.petconnectbe.repositories.UserRepository;
import com.petconnectbe.services.FileStorageService;
import com.petconnectbe.services.PetService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;
    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;

    @Override
    @Transactional
    public PetDto createPet(PetDto petDto, MultipartFile image) {
        User tutor = userRepository.findById(petDto.getTutorId())
            .orElseThrow(() -> new EntityNotFoundException("Tutor não encontrado com o ID: " + petDto.getTutorId()));

        Pet pet = toEntity(petDto);
        pet.setTutor(tutor); // Associa a entidade User completa

        if (image != null && !image.isEmpty()) {
            String imageUrl = fileStorageService.store(image);
            pet.setImageUrl(imageUrl);
        }

        Pet savedPet = petRepository.save(pet);
        return toDto(savedPet);
    }

    @Override
    @Transactional
    public PetDto updatePetImage(Integer id, MultipartFile image) {
        Pet pet = petRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Pet não encontrado com o ID: " + id));

        if (image != null && !image.isEmpty()) {
            String imageUrl = fileStorageService.store(image);
            pet.setImageUrl(imageUrl);
        }

        Pet updatedPet = petRepository.save(pet);
        return toDto(updatedPet);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PetDto> findById(Integer id) {
        return petRepository.findById(id).map(this::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PetDto> findAll() {
        return petRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PetDto update(Integer id, PetDto petDto) {
        Pet existingPet = petRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pet não encontrado com o ID: " + id));

        BeanUtils.copyProperties(petDto, existingPet, "id", "tutorId", "imageUrl");

        Pet updatedPet = petRepository.save(existingPet);
        return toDto(updatedPet);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        if (!petRepository.existsById(id)) {
            throw new EntityNotFoundException("Pet não encontrado com o ID: " + id);
        }
        petRepository.deleteById(id);
    }

    private PetDto toDto(Pet pet) {
        if (pet == null) {
            return null;
        }
        PetDto dto = new PetDto();
        BeanUtils.copyProperties(pet, dto);
        if (pet.getTutor() != null) {
            dto.setTutorId(pet.getTutor().getId());
        }
        return dto;
    }

    private Pet toEntity(PetDto dto) {
        if (dto == null) {
            return null;
        }
        Pet pet = new Pet();
        BeanUtils.copyProperties(dto, pet, "tutorId");
        return pet;
    }
}
