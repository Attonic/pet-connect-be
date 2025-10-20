package com.petconnectbe.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petconnectbe.dto.PetDto;
import com.petconnectbe.services.PetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Controlador REST para gerenciar as operações relacionadas a Pets.
 * Expõe endpoints para criar, ler, atualizar e deletar informações sobre os animais de estimação.
 */
@RestController
@RequestMapping("/pets")
public class PetController {

    private final PetService petService;
    private final ObjectMapper objectMapper; // Usado para desserializar o JSON do pet

    /**
     * Construtor para injeção de dependências do PetService e ObjectMapper.
     *
     * @param petService O serviço para lógica de negócios de Pet.
     * @param objectMapper O mapeador de objetos para manipulação de JSON.
     */
    public PetController(PetService petService, ObjectMapper objectMapper) {
        this.petService = petService;
        this.objectMapper = objectMapper;
    }

    /**
     * Endpoint para criar um novo Pet.
     * Recebe os dados do pet como uma string JSON e uma imagem (opcional) como multipart/form-data.
     *
     * @param petJson String JSON representando o objeto PetDto.
     * @param image Arquivo de imagem do pet (MultipartFile), pode ser nulo.
     * @return ResponseEntity contendo o PetDto criado e o status HTTP 201 (Created).
     *         Retorna HTTP 400 (Bad Request) se a string JSON for malformada.
     */
    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<PetDto> createPet(
            @RequestPart("pet") String petJson,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        
        try {
            // Converte a string JSON para o objeto PetDto usando o ObjectMapper
            PetDto petDto = objectMapper.readValue(petJson, PetDto.class);

            // Delega a criação do pet para a camada de serviço
            PetDto createdPet = petService.createPet(petDto, image);

            // Retorna o objeto criado e o status HTTP 201
            return new ResponseEntity<>(createdPet, HttpStatus.CREATED);
        } catch (Exception e) {
            // Logar a exceção é crucial para a depuração
            e.printStackTrace(); 
            // Retorna uma resposta de erro genérica para o cliente
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
