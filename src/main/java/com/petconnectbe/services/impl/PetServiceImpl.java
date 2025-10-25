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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;
    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;
    private final PetMapper petMapper; // Injetando o mapper

    @Override
    @Transactional
    public PetDto createPet(PetDto petDto, MultipartFile image) {
        User tutor = userRepository.findById(petDto.getTutorId())
            .orElseThrow(() -> new EntityNotFoundException("Tutor não encontrado com o ID: " + petDto.getTutorId()));

        // Usando o mapper para converter DTO para a entidade correta (Dog ou Cat)
        Pet pet = petMapper.toEntity(petDto);
        pet.setTutor(tutor); // Associa a entidade User completa

        if (image != null && !image.isEmpty()) {
            String imageUrl = fileStorageService.store(image);
            pet.setImageUrl(imageUrl);
        }

        Pet savedPet = petRepository.save(pet);
        // Usando o mapper para converter a entidade salva de volta para DTO
        return petMapper.toDto(savedPet);
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
        return petMapper.toDto(updatedPet);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PetDto> findById(Integer id) {
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
    public PetDto update(Integer id, PetDto petDto) {
        Pet existingPet = petRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pet não encontrado com o ID: " + id));

        // Cria uma entidade atualizada a partir do DTO, mas preserva o ID e o tutor
        Pet updatedData = petMapper.toEntity(petDto);
        updatedData.setId(existingPet.getId());
        updatedData.setTutor(existingPet.getTutor());

        // Como o tipo do pet pode mudar, o save() vai lidar com a atualização polimórfica
        Pet savedPet = petRepository.save(updatedData);
        return petMapper.toDto(savedPet);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        if (!petRepository.existsById(id)) {
            throw new EntityNotFoundException("Pet não encontrado com o ID: " + id);
        }
        petRepository.deleteById(id);
    }
}
