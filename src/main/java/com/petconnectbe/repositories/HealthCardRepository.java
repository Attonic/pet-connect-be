package com.petconnectbe.repositories;

import com.petconnectbe.models.HealthCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositório para a entidade HealthCard.
 * Fornece métodos para realizar operações de banco de dados para a entidade HealthCard.
 */
@Repository
public interface HealthCardRepository extends JpaRepository<HealthCard, Integer> {

    /**
     * Encontra um HealthCard pelo ID do Pet associado.
     * O Spring Data JPA cria automaticamente a implementação deste método.
     * @param petId O ID do pet.
     * @return Um Optional contendo o HealthCard se encontrado, caso contrário, vazio.
     */
    Optional<HealthCard> findByPetId(Integer petId);
}
