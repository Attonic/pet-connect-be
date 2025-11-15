
package com.petconnectbe.services.impl;

import com.petconnectbe.dto.PetDto;
import com.petconnectbe.mappers.PetMapper;
import com.petconnectbe.models.Pet;
import com.petconnectbe.models.User;
import com.petconnectbe.repositories.PetRepository;
import com.petconnectbe.repositories.UserRepository;
import com.petconnectbe.services.FileStorageService;
import com.petconnectbe.services.PetCardService;
import com.petconnectbe.services.PetService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
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
    private final PetCardService petCardService; // Injetado!
    private final FileStorageService fileStorageService;
    private final PetMapper petMapper;

    /**
     * Implementação da criação de um Pet, agora associado a um usuário pela URL.
     */
    @Override
    @Transactional
    public PetDto createPet(UUID userId, PetDto petDto, MultipartFile image) {
        // 1. Busca o usuário que será o dono do pet.
        User tutor = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário (tutor) não encontrado com o ID: " + userId));

        // 2. Converte DTO para entidade e associa o dono.
        Pet pet = petMapper.toEntity(petDto);
        pet.setTutor(tutor);

        // 3. Salva a imagem, se existir, usando o serviço de storage.
        if (image != null && !image.isEmpty()) {
            String imageUrl = fileStorageService.store(image);
            pet.setImageUrl(imageUrl);
        }

        // 4. Salva o pet no banco.
        Pet savedPet = petRepository.save(pet);

        // 5. DELEGADO! Cria automaticamente um PetCard para o novo pet usando o serviço.
        petCardService.createPetCardForPet(savedPet);

        // 6. Retorna o DTO do pet criado.
        return petMapper.toDto(savedPet);
    }

    /**
     * Implementação da busca de todos os pets de um usuário específico.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PetDto> findAllByUserId(UUID userId) {
        // Usa um novo método do repositório para buscar pets pelo ID do tutor.
        return petRepository.findByTutorId(userId).stream()
                .map(petMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Implementação da busca de um pet específico, garantindo que ele pertence ao usuário.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PetDto> findByIdAndUserId(UUID petId, UUID userId) {
        // Usa um novo método do repositório que busca pela combinação de ID do pet e do tutor.
        return petRepository.findByIdAndTutorId(petId, userId).map(petMapper::toDto);
    }

    /**
     * Implementação da atualização de um pet, com verificação de propriedade.
     */
    @Override
    @Transactional
    public PetDto updatePet(UUID petId, UUID userId, PetDto petDto, MultipartFile image) {
        // 1. Busca o pet, garantindo que ele pertence ao usuário logado.
        Pet existingPet = petRepository.findByIdAndTutorId(petId, userId)
                .orElseThrow(() -> new AccessDeniedException("Acesso negado. O pet não pertence ao usuário."));

        // 2. Atualiza os campos do pet existente com os dados do DTO.
        existingPet.setName(petDto.getName());
        existingPet.setWeight(petDto.getWeight());
        existingPet.setBirthDate(petDto.getBirthDate());
        //... (outros campos que possam ser atualizados)

        // 3. Atualiza a imagem se uma nova for enviada.
        if (image != null && !image.isEmpty()) {
            String imageUrl = fileStorageService.store(image);
            existingPet.setImageUrl(imageUrl);
        }

        // 4. Salva as alterações e retorna o DTO.
        Pet savedPet = petRepository.save(existingPet);
        return petMapper.toDto(savedPet);
    }

    /**
     * Implementação da deleção de um pet, com verificação de propriedade.
     */
    @Override
    @Transactional
    public void deleteByIdAndUserId(UUID petId, UUID userId) {
        // 1. Verifica se o pet a ser deletado existe e pertence ao usuário.
        if (!petRepository.existsByIdAndTutorId(petId, userId)) {
            throw new AccessDeniedException("Acesso negado. O pet não pertence ao usuário ou não foi encontrado.");
        }

        // NOTA: A lógica de deleção em cascata (PetCard, etc.) deve ser configurada na entidade com @Cascade.
        // 2. Deleta o pet.
        petRepository.deleteById(petId);
    }
}
