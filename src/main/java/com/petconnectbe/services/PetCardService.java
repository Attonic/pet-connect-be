package com.petconnectbe.services;

import com.petconnectbe.dto.PetCardDto;

public interface PetCardService {

    /**
     * Cria um novo cartão de saúde para um pet.
     * @param petCardDto O DTO contendo as informações do novo PetCard.
     * @return O DTO do PetCard criado.
     */
    PetCardDto createPetCard(PetCardDto petCardDto);

    /**
     * Busca o cartão de saúde associado a um pet específico.
     * @param petId O ID do pet.
     * @return O DTO do PetCard encontrado.
     */
    PetCardDto getPetCardByPetId(Integer petId);

    /**
     * Atualiza as informações de um cartão de saúde existente.
     * @param id O ID do PetCard a ser atualizado.
     * @param petCardDto O DTO com as novas informações.
     * @return O DTO do PetCard atualizado.
     */
    PetCardDto updatePetCard(Integer id, PetCardDto petCardDto);

    /**
     * Deleta um cartão de saúde pelo seu ID.
     * @param id O ID do PetCard a ser deletado.
     */
    void deletePetCard(Integer id);
}
