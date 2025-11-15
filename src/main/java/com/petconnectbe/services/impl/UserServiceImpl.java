package com.petconnectbe.services.impl;

import com.petconnectbe.dto.UserDto;
import com.petconnectbe.models.Clinica;
import com.petconnectbe.models.Ong;
import com.petconnectbe.models.Tutor;
import com.petconnectbe.models.User;
import com.petconnectbe.repositories.UserRepository;
import com.petconnectbe.services.AddressService;
import com.petconnectbe.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AddressService addressService;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserDto save(UserDto userDto) {
        userRepository.findByEmail(userDto.getEmail()).ifPresent(user -> {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email já cadastrado.");
        });

        User user = toEntity(userDto);

        // Garante que estamos criando uma nova entidade, ignorando IDs do DTO.
        user.setId(null);
        if (user.getAddress() != null) {
            user.getAddress().setId(null);
        }

        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

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

        BeanUtils.copyProperties(userDto, user, "id", "password", "email", "cpfOrCnpj", "birthOrFoundationDate");

        if (user instanceof Tutor) {
            Tutor tutor = (Tutor) user;
            tutor.setCpf(userDto.getCpfOrCnpj());
            tutor.setBirthDate(userDto.getBirthOrFoundationDate());
        } else if (user instanceof Ong) {
            Ong ong = (Ong) user;
            ong.setCnpj(userDto.getCpfOrCnpj());
            ong.setFoundationDate(userDto.getBirthOrFoundationDate());
        } else if (user instanceof Clinica) {
            Clinica clinica = (Clinica) user;
            clinica.setCnpj(userDto.getCpfOrCnpj());
            clinica.setFoundationDate(userDto.getBirthOrFoundationDate());
        }

        if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
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

    private UserDto toDto(User user) {
        if (user == null) {
            return null;
        }
        UserDto dto = new UserDto();
        BeanUtils.copyProperties(user, dto, "password");

        if (user instanceof Tutor) {
            Tutor tutor = (Tutor) user;
            dto.setType("TUTOR");
            dto.setCpfOrCnpj(tutor.getCpf());
            dto.setBirthOrFoundationDate(tutor.getBirthDate());
        } else if (user instanceof Ong) {
            Ong ong = (Ong) user;
            dto.setType("ONG");
            dto.setCpfOrCnpj(ong.getCnpj());
            dto.setBirthOrFoundationDate(ong.getFoundationDate());
        } else if (user instanceof Clinica) {
            Clinica clinica = (Clinica) user;
            dto.setType("CLINICA");
            dto.setCpfOrCnpj(clinica.getCnpj());
            dto.setBirthOrFoundationDate(clinica.getFoundationDate());
        }

        if (user.getAddress() != null) {
            dto.setAddress(addressService.toDto(user.getAddress()));
        }
        return dto;
    }

    private User toEntity(UserDto dto) {
        if (dto == null || dto.getType() == null) {
            throw new IllegalArgumentException("UserDto ou tipo de usuário não pode ser nulo");
        }

        User user;
        String type = dto.getType().toUpperCase();

        switch (type) {
            case "TUTOR":
                Tutor tutor = new Tutor();
                tutor.setCpf(dto.getCpfOrCnpj());
                tutor.setBirthDate(dto.getBirthOrFoundationDate());
                user = tutor;
                break;
            case "ONG":
                Ong ong = new Ong();
                ong.setCnpj(dto.getCpfOrCnpj());
                ong.setFoundationDate(dto.getBirthOrFoundationDate());
                user = ong;
                break;
            case "CLINICA":
                Clinica clinica = new Clinica();
                clinica.setCnpj(dto.getCpfOrCnpj());
                clinica.setFoundationDate(dto.getBirthOrFoundationDate());
                user = clinica;
                break;
            default:
                throw new IllegalArgumentException("Tipo de usuário desconhecido: " + dto.getType());
        }

        BeanUtils.copyProperties(dto, user, "id", "cpfOrCnpj", "birthOrFoundationDate");

        if (dto.getAddress() != null) {
            user.setAddress(addressService.toEntity(dto.getAddress()));
        }
        return user;
    }
}
