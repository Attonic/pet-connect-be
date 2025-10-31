package com.petconnectbe.services;

import com.petconnectbe.dto.PetCardDto;
import java.util.UUID;

public interface PetCardService {

    /**
     * Cria um novo PetCard para um pet.
     * @param petCardDto O DTO contendo as informações do novo PetCard.
     * @return O DTO do PetCard criado.
     */
    PetCardDto createPetCard(PetCardDto petCardDto);

    /**
     * Recupera o PetCard associado a um pet específico.
     * @param petId O ID do pet.
     * @return O DTO do PetCard encontrado.
     */
    PetCardDto getPetCardByPetId(UUID petId);

    /**
     * Atualiza as informações de um PetCard existente.
     * @param id O ID do PetCard a ser atualizado.
     * @param petCardDto O DTO com as novas informações.
     * @return O DTO do PetCard atualizado.
     */
    PetCardDto updatePetCard(UUID id, PetCardDto petCardDto);

    /**
     * Deleta um PetCard pelo seu ID.
     * @param id O ID do PetCard a ser deletado.
     */
    void deletePetCard(UUID id);
}
