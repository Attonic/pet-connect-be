package com.petconnectbe.controllers.auth;

import com.petconnectbe.dto.auth.LoginDto;
import com.petconnectbe.dto.auth.LoginResponseDto;
import com.petconnectbe.dto.auth.UserWithRefreshTokenDto;
import com.petconnectbe.models.Clinica;
import com.petconnectbe.models.Ong;
import com.petconnectbe.models.Tutor;
import com.petconnectbe.models.User;
import com.petconnectbe.security.TokenService;
import com.petconnectbe.repositories.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginDto loginDto) {

        var usernamePassword = new UsernamePasswordAuthenticationToken(loginDto.email(), loginDto.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var accessToken = tokenService.generateAccessToken((User) auth.getPrincipal());
        var refreshToken = tokenService.generateRefreshToken((User) auth.getPrincipal());

        User user = (User) auth.getPrincipal();

        UserWithRefreshTokenDto userDto = new UserWithRefreshTokenDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                getUserType(user),
                refreshToken
        );

        LoginResponseDto response = new LoginResponseDto(userDto, accessToken);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDto> refresh(@RequestBody String refreshToken) {
        String userEmail = tokenService.validateToken(refreshToken);

        if (userEmail.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid refresh token.");
        }

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid refresh token."));

        var newAccessToken = tokenService.generateAccessToken(user);
        var newRefreshToken = tokenService.generateRefreshToken(user);

        UserWithRefreshTokenDto userDto = new UserWithRefreshTokenDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                getUserType(user),
                newRefreshToken
        );

        LoginResponseDto response = new LoginResponseDto(userDto, newAccessToken);

        return ResponseEntity.ok(response);
    }

    private String getUserType(User user) {
        if (user instanceof Tutor) {
            return "TUTOR";
        } else if (user instanceof Ong) {
            return "ONG";
        } else if (user instanceof Clinica) {
            return "CLINICA";
        }
        return "UNKNOWN";
    }
}