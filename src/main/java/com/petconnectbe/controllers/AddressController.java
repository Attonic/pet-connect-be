package com.petconnectbe.controllers;

import com.petconnectbe.dto.AddressDto;
import com.petconnectbe.services.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/addresses")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    public ResponseEntity<AddressDto> create(@Valid @RequestBody AddressDto addressDto){
        AddressDto addressDto1 = addressService.save(addressDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(addressDto1);
    }

    @GetMapping
    public ResponseEntity<List<AddressDto>> findAll(){
        List<AddressDto> addressDtos = addressService.findAll();
        return ResponseEntity.ok(addressDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressDto> findById(@PathVariable UUID id){
        return addressService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
