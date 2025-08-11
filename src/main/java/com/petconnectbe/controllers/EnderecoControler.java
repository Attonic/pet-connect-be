package com.petconnectbe.controllers;

import com.petconnectbe.dto.EnderecoDto;
import com.petconnectbe.services.EnderecoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(name = "/enderecos")
@RequiredArgsConstructor
public class EnderecoControler {

    private final EnderecoService enderecoService;

    @PostMapping
    public ResponseEntity<EnderecoDto> create(@Valid @RequestBody EnderecoDto enderecoDto){
        EnderecoDto endereco = enderecoService.salvar(enderecoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(endereco);
    }



}
