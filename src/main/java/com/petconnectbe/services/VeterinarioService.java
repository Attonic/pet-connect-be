package com.petconnectbe.services;

import com.petconnectbe.dto.VeterinarioDto;

import java.util.List;
import java.util.UUID;

public interface VeterinarioService {

    VeterinarioDto salvar(VeterinarioDto veterinarioDto);

    VeterinarioDto findById(UUID id);

    List<VeterinarioDto> findAll();

}
