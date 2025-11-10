package com.petconnectbe.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.petconnectbe.models.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${jwt.secret-key}")
    private String secret;

    // Gerar o Access Token (Expiração curta)
    public String generateAccessToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("pet-connect-api")
                    .withSubject(user.getEmail())
                    .withExpiresAt(genAccessTokenExpirationDate()) // Expira em 2 horas
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar access token", exception);
        }
    }

    // NOVO METODO: Gerar o Refresh Token como um JWT (Expiração longa)
    public String generateRefreshToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("pet-connect-api")
                    .withSubject(user.getEmail())
                    .withClaim("id", user.getId().toString())
                    .withExpiresAt(genRefreshTokenExpirationDate()) // Expira em 7 dias
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar refresh token", exception);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("pet-connect-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception){
            return "";
        }
    }

    private Instant genAccessTokenExpirationDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

    private Instant genRefreshTokenExpirationDate(){
        return LocalDateTime.now().plusDays(7).toInstant(ZoneOffset.of("-03:00"));
    }
}