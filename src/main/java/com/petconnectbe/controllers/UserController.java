package com.petconnectbe.controllers;

import com.petconnectbe.dto.UserDto;
import com.petconnectbe.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    private final UserService userService;


    @PostMapping
    public ResponseEntity<UserDto> criarVeterinario(@RequestBody UserDto userDto){
        UserDto userDtoCriado = userService.save(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDtoCriado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable UUID id){
        UserDto userDto = userService.findById(id);
        return  ResponseEntity.ok(userDto);
    }

    @GetMapping()
    public ResponseEntity<List<UserDto>> findAll(){
        List<UserDto> userDtos = userService.findAll();
        return ResponseEntity.ok(userDtos);
    }



}
