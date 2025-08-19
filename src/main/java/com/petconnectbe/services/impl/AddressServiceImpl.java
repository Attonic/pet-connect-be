package com.petconnectbe.services.impl;

import com.petconnectbe.dto.AddressDto;
import com.petconnectbe.models.Address;
import com.petconnectbe.repositories.AddressRepository;
import com.petconnectbe.services.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;


    @Override
    public AddressDto save(AddressDto dto) {
        Address address = new Address();
        address.setCep(dto.getCep());
        address.setStreet(dto.getStreet());
        address.setNeighborhood(dto.getNeighborhood());
        address.setCity(dto.getCity());
        address.setUf(dto.getUf());

        Address salvo = addressRepository.save(address);
        return toDto(salvo);
    }

    @Override
    public List<AddressDto> findAll() {
        return addressRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public AddressDto findById(UUID id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Endereço não encontrado."));
        return toDto(address);
    }



    @Override
    public AddressDto toDto(Address address) {
        AddressDto dto = new AddressDto();
        dto.setCep(address.getCep());
        dto.setStreet(address.getStreet());
        dto.setNeighborhood(address.getNeighborhood());
        dto.setCity(address.getCity());
        dto.setUf(address.getUf());
        return dto;
    }

    @Override
    public Address toEntity(AddressDto dto) {
        if (dto == null) {
            return null;
        }
        Address address = new Address();
        address.setCep(dto.getCep());
        address.setStreet(dto.getStreet());
        address.setNeighborhood(dto.getNeighborhood());
        address.setCity(dto.getCity());
        address.setUf(dto.getUf());
        return address;
    }
}
