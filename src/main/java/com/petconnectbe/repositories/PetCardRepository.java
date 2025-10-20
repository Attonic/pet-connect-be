package com.petconnectbe.repositories;

import com.petconnectbe.models.PetCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositório para a entidade PetCard.
 * Fornece métodos para realizar operações de banco de dados para a entidade PetCard.
 */
@Repository
public interface PetCardRepository extends JpaRepository<PetCard, Integer> {

    /**
     * Encontra um PetCard pelo ID do Pet associado.
     * @param petId O ID do pet.
     * @return Um Optional contendo o PetCard se encontrado, caso contrário, vazio.
     */
    Optional<PetCard> findByPetId(Integer petId);
}
