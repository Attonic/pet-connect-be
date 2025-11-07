package com.petconnectbe.repositories;

import com.petconnectbe.models.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositório Spring Data JPA para a entidade {@link Pet}.
 * <p>
 * Fornece métodos CRUD (Create, Read, Update, Delete) para a entidade Pet
 * sem a necessidade de implementação explícita, além de permitir a definição de
 * métodos de consulta personalizados que o Spring Data JPA implementará automaticamente.
 */
public interface PetRepository extends JpaRepository<Pet, Integer> {
}
