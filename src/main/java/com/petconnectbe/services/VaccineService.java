package com.petconnectbe.services;

import com.petconnectbe.dto.VaccineDto;

public interface VaccineService {
    VaccineDto createVaccine(Integer petCardId, VaccineDto vaccineDto);
}
