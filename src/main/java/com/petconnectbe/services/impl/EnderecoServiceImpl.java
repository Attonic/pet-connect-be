package com.petconnectbe.services.impl;

import com.petconnectbe.dto.EnderecoDto;
import com.petconnectbe.models.Endereco;
import com.petconnectbe.repositories.EnderecoRepository;
import com.petconnectbe.services.EnderecoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnderecoServiceImpl implements EnderecoService {

    private final EnderecoRepository enderecoRepository;


    @Override
    public EnderecoDto salvar(EnderecoDto dto) {
        Endereco endereco = new Endereco();
        endereco.setCep(dto.getCep());
        endereco.setRua(dto.getRua());
        endereco.setBairro(dto.getBairro());
        endereco.setComplemento(dto.getComplemento());
        endereco.setCidade(dto.getCidade());
        endereco.setEstadoUF(dto.getEstadoUF());

        Endereco salvo = enderecoRepository.save(endereco);
        return toDto(salvo);
    }

    @Override
    public List<EnderecoDto> findAll() {
        return enderecoRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public EnderecoDto findById(UUID id) {
        Endereco endereco = enderecoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Endereço não encontrado."));
        return toDto(endereco);
    }



    @Override
    public EnderecoDto toDto(Endereco endereco) {
        EnderecoDto dto = new EnderecoDto();
        dto.setCep(endereco.getCep());
        dto.setRua(endereco.getRua());
        dto.setBairro(endereco.getBairro());
        dto.setComplemento(endereco.getComplemento());
        dto.setCidade(endereco.getCidade());
        dto.setEstadoUF(endereco.getEstadoUF());
        return dto;
    }

    @Override
    public Endereco toEntity(EnderecoDto dto) {
        if (dto == null) {
            return null;
        }
        Endereco endereco = new Endereco();
        endereco.setCep(dto.getCep());
        endereco.setRua(dto.getRua());
        endereco.setBairro(dto.getBairro());
        endereco.setComplemento(dto.getComplemento());
        endereco.setCidade(dto.getCidade());
        endereco.setEstadoUF(dto.getEstadoUF());
        return endereco;
    }
}
