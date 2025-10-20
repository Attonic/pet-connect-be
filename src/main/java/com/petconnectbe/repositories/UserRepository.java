package com.petconnectbe.repositories;

import com.petconnectbe.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repositório Spring Data JPA para a entidade {@link User}.
 * <p>
 * Fornece métodos CRUD (Create, Read, Update, Delete) para a entidade User,
 * além de permitir a definição de métodos de consulta personalizados.
 */
public interface UserRepository extends JpaRepository<User, UUID> {

    /**
     * Busca um usuário pelo seu endereço de e-mail.
     * <p>
     * Como o campo de e-mail é definido como único na entidade {@link User},
     * este método retornará no máximo um usuário.
     *
     * @param email O e-mail a ser pesquisado.
     * @return Um {@link Optional} contendo o {@link User} encontrado, ou {@link Optional#empty()} se nenhum usuário com o e-mail fornecido for encontrado.
     */
    Optional<User> findByEmail(String email);
}
