package com.petconnectbe.dto.auth;

import java.util.UUID;

public record UserWithRefreshTokenDto(
        UUID id,
        String name,
        String email,
        String role,
        String refreshToken
) {}