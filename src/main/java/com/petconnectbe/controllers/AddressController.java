package com.petconnectbe.controllers;

import com.petconnectbe.dto.AddressDto;
import com.petconnectbe.services.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    public ResponseEntity<AddressDto> create(@Valid @RequestBody AddressDto addressDto){
        AddressDto addressDto1 = addressService.save(addressDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(addressDto1);
    }



}
