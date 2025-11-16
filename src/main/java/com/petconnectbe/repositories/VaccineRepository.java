
package com.petconnectbe.repositories;

import com.petconnectbe.models.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface VaccineRepository extends JpaRepository<Vaccine, UUID> {

    /**
     * Encontra todas as vacinas associadas a um pet específico, navegando através da entidade PetCard.
     * O Spring Data JPA cria a consulta automaticamente com base no nome do método.
     * A convenção "PetCard_Pet_Id" instrui o Spring a fazer um JOIN através de
     * Vaccine -> PetCard -> Pet e filtrar pelo ID do Pet.
     *
     * @param petId O UUID do Pet cujas vacinas estão sendo pesquisadas.
     * @return Uma lista de entidades Vaccine associadas ao pet especificado.
     */
    List<Vaccine> findByPetCard_Pet_Id(UUID petId);
}
