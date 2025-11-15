
package com.petconnectbe.services.impl;

import com.petconnectbe.dto.AddressDto;
import com.petconnectbe.models.Address;
import com.petconnectbe.models.User;
import com.petconnectbe.repositories.AddressRepository;
import com.petconnectbe.repositories.UserRepository;
import com.petconnectbe.services.AddressService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<AddressDto> findAddressByUserId(UUID userId) {
        // Busca o endereço diretamente pelo ID do usuário associado.
        return addressRepository.findByTutorId(userId).map(this::toDto);
    }

    @Override
    @Transactional
    public AddressDto createOrUpdateAddress(UUID userId, AddressDto addressDto) {
        // 1. Garante que o usuário exista.
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com o ID: " + userId));

        // 2. Verifica se o usuário já tem um endereço (lógica de "upsert").
        Address address = addressRepository.findByTutorId(userId).orElse(new Address());

        // 3. Copia as propriedades do DTO para a entidade.
        BeanUtils.copyProperties(addressDto, address, "id");

        // 4. Associa o endereço ao usuário.
        address.setTutor(user);

        // 5. Salva a entidade e retorna o DTO.
        Address savedAddress = addressRepository.save(address);
        return toDto(savedAddress);
    }

    @Override
    @Transactional
    public void deleteAddressByUserId(UUID userId) {
        // 1. Encontra o endereço associado ao usuário.
        Address address = addressRepository.findByTutorId(userId)
            .orElseThrow(() -> new EntityNotFoundException("Endereço não encontrado para o usuário com ID: " + userId));

        // 2. Desassocia o endereço do usuário (importante para o JPA gerenciar a relação).
        User user = address.getTutor();
        if (user != null) {
            user.setAddress(null);
            userRepository.save(user);
        }

        // 3. Deleta o endereço.
        addressRepository.delete(address);
    }

    // --- Métodos de Conversão ---

    @Override
    public AddressDto toDto(Address address) {
        if (address == null) {
            return null;
        }
        AddressDto dto = new AddressDto();
        BeanUtils.copyProperties(address, dto);
        return dto;
    }

    @Override
    public Address toEntity(AddressDto dto) {
        if (dto == null) {
            return null;
        }
        Address address = new Address();
        BeanUtils.copyProperties(dto, address);
        return address;
    }
}
