package com.petconnectbe.repositories;

import com.petconnectbe.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repositório Spring Data JPA para a entidade {@link Address}.
 * <p>
 * Fornece métodos CRUD (Create, Read, Update, Delete) para a entidade Address,
 * além de permitir a definição de métodos de consulta personalizados.
 */
public interface AddressRepository extends JpaRepository<Address, UUID> {

    /**
     * Busca o primeiro endereço encontrado correspondente a uma sigla de Unidade Federativa (UF).
     * <p>
     * Nota: Este método retornará apenas um resultado, mesmo que múltiplos endereços
     * compartilhem a mesma UF.
     *
     * @param uf A sigla do estado (UF) para a busca. Ex: "SP", "RJ".
     * @return Um {@link Optional} contendo o {@link Address} encontrado, ou {@link Optional#empty()} se nenhum for encontrado.
     */
    Optional<Address> findByUf(String uf);
}
