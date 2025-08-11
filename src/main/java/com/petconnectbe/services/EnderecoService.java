package com.petconnectbe.services;

import com.petconnectbe.dto.EnderecoDto;
import com.petconnectbe.models.Endereco;

import java.util.List;
import java.util.UUID;

public interface EnderecoService {

    EnderecoDto salvar(EnderecoDto dto);

    List<EnderecoDto> findAll();

    EnderecoDto findById(UUID id);


   EnderecoDto toDto(Endereco endereco);

   Endereco toEntity(EnderecoDto enderecoDto);
}
