package com.petconnectbe.dto.auth;

public record LoginResponseDto(
        UserWithRefreshTokenDto user,
        String token
) {}