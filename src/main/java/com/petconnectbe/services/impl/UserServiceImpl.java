package com.petconnectbe.services.impl;

import com.petconnectbe.dto.UserDto;
import com.petconnectbe.models.User;
import com.petconnectbe.repositories.UserRepository;
import com.petconnectbe.services.AddressService;
import com.petconnectbe.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AddressService addressService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto save(UserDto userDto) {

        if (userRepository.findByEmail(userDto.getEmail()).isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email já cadastrado.");
        }

        User user = new User();
        user.setName(userDto.getName());
        user.setType(userDto.getType());
        user.setEmail(userDto.getEmail());
        user.setPhone(userDto.getPhone());
        user.setBirthOrFoundationDate(userDto.getBirthOrFoundationDate());
        user.setCpfOrCnpj(userDto.getCpfOrCnpj());

        // Criptografa a senha antes de salvar
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));


        if(userDto.getEndereco() != null){
            user.setAddress(addressService.toEntity(userDto.getEndereco()));
        }

        User salvo = userRepository.save(user);
        return toDto(salvo);
    }

    @Override
    public UserDto findById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Veterinário não encontrado"));
        return toDto(user);
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public UserDto toDto(User user) {
        UserDto userDto = new UserDto();

        userDto.setName(user.getName());
        userDto.setType(user.getType());
        userDto.setEmail(user.getEmail());
        userDto.setPhone(user.getPhone());
        userDto.setBirthOrFoundationDate(user.getBirthOrFoundationDate());
        userDto.setCpfOrCnpj(user.getCpfOrCnpj());

        // A senha não deve ser retornada no DTO por questões de segurança.
        // Se você precisa exibir algum dado relacionado, como a data de cadastro,
        // é melhor criar um DTO específico para a visualização.
        // Remova a linha abaixo para não expor a senha no retorno da API.
        // userDto.setPassword(user.getPassword());

        if(user.getAddress() != null){
            userDto.setEndereco(addressService.toDto(user.getAddress()));
        }
        return userDto;
    }
}