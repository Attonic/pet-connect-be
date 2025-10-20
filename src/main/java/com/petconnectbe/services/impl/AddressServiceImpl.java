package com.petconnectbe.services.impl;

import com.petconnectbe.dto.AddressDto;
import com.petconnectbe.models.Address;
import com.petconnectbe.repositories.AddressRepository;
import com.petconnectbe.services.AddressService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementação do serviço {@link AddressService} para gerenciar endereços.
 * <p>
 * Esta classe implementa a lógica de negócio para operações relacionadas a endereços,
 * utilizando o {@link AddressRepository} para interagir com o banco de dados.
 * As transações são gerenciadas pelo Spring.
 */
@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    /**
     * {@inheritDoc}
     * <p>
     * Se o DTO fornecido tiver um ID, o método tentará atualizar o endereço existente.
     * Caso contrário, um novo endereço será criado.
     *
     * @throws EntityNotFoundException se um ID for fornecido mas o endereço correspondente não for encontrado.
     */
    @Override
    @Transactional
    public AddressDto save(AddressDto dto) {
        Address address;
        if (dto.getId() != null) {
            address = addressRepository.findById(dto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Endereço não encontrado com o ID: " + dto.getId()));
        } else {
            address = new Address();
        }

        address.setCep(dto.getCep());
        address.setStreet(dto.getStreet());
        address.setNeighborhood(dto.getNeighborhood());
        address.setCity(dto.getCity());
        address.setUf(dto.getUf());

        Address savedAddress = addressRepository.save(address);
        return toDto(savedAddress);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<AddressDto> findAll() {
        return addressRepository.findAll()
            .stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AddressDto> findById(UUID id) {
        return addressRepository.findById(id).map(this::toDto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AddressDto toDto(Address address) {
        if (address == null) {
            return null;
        }
        AddressDto dto = new AddressDto();
        dto.setId(address.getId());
        dto.setCep(address.getCep());
        dto.setStreet(address.getStreet());
        dto.setNeighborhood(address.getNeighborhood());
        dto.setCity(address.getCity());
        dto.setUf(address.getUf());
        return dto;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Address toEntity(AddressDto dto) {
        if (dto == null) {
            return null;
        }
        Address address = new Address();
        address.setId(dto.getId());
        address.setCep(dto.getCep());
        address.setStreet(dto.getStreet());
        address.setNeighborhood(dto.getNeighborhood());
        address.setCity(dto.getCity());
        address.setUf(dto.getUf());
        return address;
    }
}
