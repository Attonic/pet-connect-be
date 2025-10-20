package com.petconnectbe.services;

import com.petconnectbe.dto.UserDto;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    UserDto save(UserDto userDto);

    Optional<UserDto> findById(UUID id);

    List<UserDto> findAll();

    UserDto update(UUID id, UserDto userDto);

    void deleteById(UUID id);

    UserDto updateUserLastname(UUID id, String lastname);

}
