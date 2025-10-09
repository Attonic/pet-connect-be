package com.petconnectbe.dto.auth;

import java.util.UUID;

public record TokenDto(
        UUID id,
        String name,
        String email,
        String role,
        String token,
        String refreshToken
) {}