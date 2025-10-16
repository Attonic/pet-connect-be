package com.petconnectbe.repositories;

import com.petconnectbe.models.HealthCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for the HealthCard entity.
 * Provides methods to perform database operations for the HealthCard entity.
 */
@Repository
public interface HealthCardRepository extends JpaRepository<HealthCard, Integer> {

    /**
     * Finds a HealthCard by the ID of the associated Pet.
     * Spring Data JPA automatically creates the implementation of this method.
     * @param petId The ID of the pet.
     * @return An Optional containing the HealthCard if found, otherwise empty.
     */
    Optional<HealthCard> findByPetId(Integer petId);
}
