package com.petconnectbe.services;

import com.petconnectbe.dto.VaccineDto;

import java.util.UUID;

public interface VaccineService {
    VaccineDto createVaccine(UUID petCardId, VaccineDto vaccineDto);
}
