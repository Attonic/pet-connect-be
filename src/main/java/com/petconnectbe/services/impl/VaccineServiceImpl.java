package com.petconnectbe.services.impl;

import com.petconnectbe.dto.VaccineDto;
import com.petconnectbe.models.PetCard;
import com.petconnectbe.models.Vaccine;
import com.petconnectbe.repositories.PetCardRepository;
import com.petconnectbe.repositories.VaccineRepository;
import com.petconnectbe.services.VaccineService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VaccineServiceImpl implements VaccineService {

    private final VaccineRepository vaccineRepository;
    private final PetCardRepository petCardRepository;

    @Override
    @Transactional
    public VaccineDto createVaccine(Integer petCardId, VaccineDto vaccineDto) {
        PetCard petCard = petCardRepository.findById(petCardId)
                .orElseThrow(() -> new EntityNotFoundException("PetCard não encontrado com o ID: " + petCardId));

        Vaccine vaccine = toEntity(vaccineDto);
        vaccine.setPetCard(petCard); // Associa a vacina ao PetCard

        Vaccine savedVaccine = vaccineRepository.save(vaccine);
        return toDto(savedVaccine);
    }

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
