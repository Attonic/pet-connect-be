package com.petconnectbe.services.impl;

import com.petconnectbe.dto.VeterinarioDto;
import com.petconnectbe.models.Veterinario;
import com.petconnectbe.repositories.VeterinarioRepository;
import com.petconnectbe.services.EnderecoService;
import com.petconnectbe.services.VeterinarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VeterinarioServiceImpl implements VeterinarioService {

    private final VeterinarioRepository veterinarioRepository;
    private final EnderecoService enderecoService;


    @Override
    public VeterinarioDto salvar(VeterinarioDto veterinarioDto) {
        if (veterinarioRepository.findByEmail(veterinarioDto.getEmail()).isPresent()){
            throw new RuntimeException("Email já cadastrado.");
        }
        if (veterinarioRepository.findByCrmv(veterinarioDto.getCrmv()).isPresent()){
            throw new RuntimeException("CRMV já cadastrado.");
        }

        Veterinario veterinario = new Veterinario();
        veterinario.setNome(veterinarioDto.getNome());
        veterinario.setEmail(veterinarioDto.getEmail());
        veterinario.setCrmv(veterinarioDto.getCrmv());
        veterinario.setSenha(veterinarioDto.getSenha());
        veterinario.setDataNascimento(veterinarioDto.getDataNascimento());

        if(veterinarioDto.getEndereco() != null){
            veterinario.setEndereco(enderecoService.toEntity(veterinarioDto.getEndereco()));
        }

        Veterinario salvo = veterinarioRepository.save(veterinario);
        return toDto(salvo);
    }

    @Override
    public VeterinarioDto findById(UUID id) {
        Veterinario veterinario = veterinarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Veterinário não encontrado"));
        return toDto(veterinario);
    }

    @Override
    public List<VeterinarioDto> findAll() {
        return veterinarioRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }



    public VeterinarioDto toDto(Veterinario veterinario) {
        VeterinarioDto dto = new VeterinarioDto();

        dto.setNome(veterinario.getNome());
        dto.setEmail(veterinario.getEmail());
        dto.setDataNascimento(veterinario.getDataNascimento());
        dto.setCrmv(veterinario.getCrmv());
        dto.setSenha(veterinario.getSenha());

        if(veterinario.getEndereco() != null){
            dto.setEndereco(enderecoService.toDto(veterinario.getEndereco()));
        }
        return dto;

    }


}
