package com.petconnectbe.services;

import com.petconnectbe.dto.AddressDto;
import com.petconnectbe.models.Address;

import java.util.List;
import java.util.UUID;

public interface AddressService {

    AddressDto save(AddressDto dto);

    List<AddressDto> findAll();

    AddressDto findById(UUID id);


   AddressDto toDto(Address address);

   Address toEntity(AddressDto addressDto);
}
