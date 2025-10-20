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

/**
 * Implementação do serviço {@link PetService} para gerenciar os dados dos Pets.
 * <p>
 * Esta classe orquestra as operações de negócio relacionadas a pets, como a
 * criação,
 * interagindo com os repositórios e outros serviços (como o
 * {@link FileStorageService})
 * para cumprir suas responsabilidades.
 */
@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;
    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;

    /**
     * {@inheritDoc}
     *
     * @throws EntityNotFoundException se o tutor (usuário) não for encontrado.
     */
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

    /**
     * Converte uma entidade {@link Pet} para seu respectivo {@link PetDto}.
     */
    private PetDto toDto(Pet pet) {
        if (pet == null) {
            return null;
        }
        PetDto dto = new PetDto();
        BeanUtils.copyProperties(pet, dto);
        if (pet.getTutor() != null) {
            // No DTO, nós definimos apenas o ID do tutor.
            dto.setTutorId(pet.getTutor().getId());
        }
        return dto;
    }

    /**
     * Converte um {@link PetDto} para sua respectiva entidade {@link Pet}.
     */
    private Pet toEntity(PetDto dto) {
        if (dto == null) {
            return null;
        }
        Pet pet = new Pet();
        // 
        // Copia as propriedades, mas ignora o tutorId, pois ele será tratado separadamente.
        BeanUtils.copyProperties(dto, pet, "tutorId");
        return pet;
    }
}
