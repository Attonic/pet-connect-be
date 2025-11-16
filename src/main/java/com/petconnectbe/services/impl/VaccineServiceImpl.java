
package com.petconnectbe.services.impl;

import com.petconnectbe.dto.VaccineDto;
import com.petconnectbe.models.Pet;
import com.petconnectbe.models.PetCard;
import com.petconnectbe.models.Vaccine;
import com.petconnectbe.repositories.PetRepository;
import com.petconnectbe.repositories.VaccineRepository;
import com.petconnectbe.services.VaccineService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VaccineServiceImpl implements VaccineService {

    private final VaccineRepository vaccineRepository;
    private final PetRepository petRepository;

    @Override
    @Transactional
    public VaccineDto createVaccine(UUID userId, UUID petId, VaccineDto vaccineDto) {
        // 1. Validação de segurança: o pet pertence ao usuário?
        Pet pet = petRepository.findByIdAndTutorId(petId, userId)
            .orElseThrow(() -> new AccessDeniedException("Acesso negado: O pet não pertence ao usuário."));

        // 2. Encontrar o PetCard associado ao pet.
        PetCard petCard = pet.getPetCard();
        if (petCard == null) {
            throw new EntityNotFoundException("PetCard não encontrado para o pet com ID: " + petId);
        }

        // 3. Criar a entidade Vaccine e associá-la ao PetCard.
        Vaccine vaccine = toEntity(vaccineDto);
        vaccine.setPetCard(petCard);

        // 4. Salvar e retornar o DTO.
        Vaccine savedVaccine = vaccineRepository.save(vaccine);
        return toDto(savedVaccine);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VaccineDto> findAllByPetId(UUID userId, UUID petId) {
        // Validação de segurança
        if (!petRepository.existsByIdAndTutorId(petId, userId)) {
            throw new AccessDeniedException("Acesso negado: O pet não pertence ao usuário.");
        }
        // Lógica de busca. Precisamos buscar pelo petId, que nos leva ao petCardId.
        return vaccineRepository.findByPetCard_Pet_Id(petId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<VaccineDto> findVaccineById(UUID userId, UUID petId, UUID vaccineId) {
        if (!petRepository.existsByIdAndTutorId(petId, userId)) {
            throw new AccessDeniedException("Acesso negado: O pet não pertence ao usuário.");
        }
        // Busca a vacina e depois valida se ela pertence ao pet correto.
        return vaccineRepository.findById(vaccineId)
                .filter(vaccine -> vaccine.getPetCard().getPet().getId().equals(petId))
                .map(this::toDto);
    }

    @Override
    @Transactional
    public VaccineDto updateVaccine(UUID userId, UUID petId, UUID vaccineId, VaccineDto vaccineDto) {
        if (!petRepository.existsByIdAndTutorId(petId, userId)) {
            throw new AccessDeniedException("Acesso negado: O pet não pertence ao usuário.");
        }

        Vaccine existingVaccine = vaccineRepository.findById(vaccineId)
                .filter(vaccine -> vaccine.getPetCard().getPet().getId().equals(petId))
                .orElseThrow(() -> new EntityNotFoundException("Vacina com ID " + vaccineId + " não encontrada para este pet."));

        // Atualiza os campos
        BeanUtils.copyProperties(vaccineDto, existingVaccine, "id", "petCardId");
        Vaccine updatedVaccine = vaccineRepository.save(existingVaccine);
        return toDto(updatedVaccine);
    }

    @Override
    @Transactional
    public void deleteVaccine(UUID userId, UUID petId, UUID vaccineId) {
        if (!petRepository.existsByIdAndTutorId(petId, userId)) {
            throw new AccessDeniedException("Acesso negado: O pet não pertence ao usuário.");
        }

        Vaccine vaccineToDelete = vaccineRepository.findById(vaccineId)
            .filter(vaccine -> vaccine.getPetCard().getPet().getId().equals(petId))
            .orElseThrow(() -> new EntityNotFoundException("Vacina com ID " + vaccineId + " não encontrada para este pet."));

        vaccineRepository.delete(vaccineToDelete);
    }

    // --- Métodos de conversão (preservados e adaptados) ---

    private VaccineDto toDto(Vaccine vaccine) {
        VaccineDto dto = new VaccineDto();
        BeanUtils.copyProperties(vaccine, dto);
        if (vaccine.getPetCard() != null) {
            dto.setPetCardId(vaccine.getPetCard().getId());
        }
        return dto;
    }

    private Vaccine toEntity(VaccineDto dto) {
        Vaccine vaccine = new Vaccine();
        BeanUtils.copyProperties(dto, vaccine, "petCardId");
        return vaccine;
    }
}
