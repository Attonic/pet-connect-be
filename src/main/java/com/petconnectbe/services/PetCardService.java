package com.petconnectbe.services;

import com.petconnectbe.dto.PetCardDto;
import com.petconnectbe.models.Pet;

import java.util.UUID;

public interface PetCardService {

    /**
     * Cria um novo PetCard para um pet.
     * Este método é chamado internamente quando um novo Pet é criado.
     * @param pet O pet para o qual o cartão será criado.
     * @return O DTO do PetCard criado.
     */
    PetCardDto createPetCardForPet(Pet pet);

    /**
     * Recupera o PetCard associado a um pet específico.
     * @param petId O ID do pet.
     * @return O DTO do PetCard encontrado.
     */
    PetCardDto getPetCardByPetId(UUID petId);

    /**
     * Atualiza as informações de um PetCard pelo ID do pet.
     * @param petId O ID do pet cujo PetCard será atualizado.
     * @param petCardDto O DTO com as novas informações.
     * @return O DTO do PetCard atualizado.
     */
    PetCardDto updatePetCardByPetId(UUID petId, PetCardDto petCardDto);

}
