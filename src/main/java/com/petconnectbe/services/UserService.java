package com.petconnectbe.services;

import com.petconnectbe.dto.UserDto;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserDto save(UserDto userDto);

    UserDto findById(UUID id);

    List<UserDto> findAll();

}
