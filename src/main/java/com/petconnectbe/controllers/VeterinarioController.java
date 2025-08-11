package com.petconnectbe.controllers;

import com.petconnectbe.dto.VeterinarioDto;
import com.petconnectbe.models.Veterinario;
import com.petconnectbe.services.VeterinarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/veterinarios")
@RequiredArgsConstructor
public class VeterinarioController {

    private final VeterinarioService veterinarioService;


    @PostMapping
    public ResponseEntity<VeterinarioDto> criarVeterinario(@RequestBody VeterinarioDto veterinarioDto){
        VeterinarioDto veterinarioDtoCriado = veterinarioService.salvar(veterinarioDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(veterinarioDtoCriado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VeterinarioDto> findById(@PathVariable UUID id){
        VeterinarioDto veterinarioDto = veterinarioService.findById(id);
        return  ResponseEntity.ok(veterinarioDto);
    }

    @GetMapping()
    public ResponseEntity<List<VeterinarioDto>> findAll(){
        List<VeterinarioDto> veterinarioDtos = veterinarioService.findAll();
        return ResponseEntity.ok(veterinarioDtos);
    }



}
