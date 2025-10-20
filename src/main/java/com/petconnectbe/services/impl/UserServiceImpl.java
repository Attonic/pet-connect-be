package com.petconnectbe.services.impl;

import com.petconnectbe.dto.UserDto;
import com.petconnectbe.models.User;
import com.petconnectbe.repositories.UserRepository;
import com.petconnectbe.services.AddressService;
import com.petconnectbe.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AddressService addressService;

    @Override
    @Transactional
    public UserDto save(UserDto userDto) {
        userRepository.findByEmail(userDto.getEmail()).ifPresent(user -> {
            throw new IllegalArgumentException("Email já cadastrado: " + userDto.getEmail());
        });

        User user = toEntity(userDto);

        User savedUser = userRepository.save(user);
        return toDto(savedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserDto> findById(UUID id) {
        return userRepository.findById(id).map(this::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> findAll() {
        return userRepository.findAll()
            .stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserDto update(UUID id, UserDto userDto) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com o ID: " + id));

        BeanUtils.copyProperties(userDto, user, "id", "password", "email");

        if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
            user.setPassword(userDto.getPassword());
        }

        if (userDto.getAddress() != null) {
            user.setAddress(addressService.toEntity(userDto.getAddress()));
        } else {
            user.setAddress(null);
        }

        User updatedUser = userRepository.save(user);
        return toDto(updatedUser);
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("Usuário não encontrado com o ID: " + id);
        }
        userRepository.deleteById(id);
    }
    
    @Override
    @Transactional
    public UserDto updateUserLastname(UUID id, String lastname) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com o ID: " + id));

        user.setLastname(lastname);

        User updatedUser = userRepository.save(user);
        return toDto(updatedUser);
    }

    private UserDto toDto(User user) {
        if (user == null) {
            return null;
        }
        UserDto dto = new UserDto();
        BeanUtils.copyProperties(user, dto, "password");

        if (user.getAddress() != null) {
            dto.setAddress(addressService.toDto(user.getAddress()));
        }
        return dto;
    }

    private User toEntity(UserDto dto) {
        if (dto == null) {
            return null;
        }
        User user = new User();
        BeanUtils.copyProperties(dto, user);

        if (dto.getAddress() != null) {
            user.setAddress(addressService.toEntity(dto.getAddress()));
        }
        return user;
    }
}
