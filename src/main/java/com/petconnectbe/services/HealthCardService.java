package com.petconnectbe.services;

import com.petconnectbe.dto.HealthCardDto;

public interface HealthCardService {

    /**
     * Cria um novo cartão de saúde para um pet.
     * @param healthCardDto O DTO contendo as informações do novo HealthCard.
     * @return O DTO do HealthCard criado.
     */
    HealthCardDto createHealthCard(HealthCardDto healthCardDto);

    /**
     * Recupera o cartão de saúde associado a um pet específico.
     * @param petId O ID do pet.
     * @return O DTO do HealthCard encontrado.
     */
    HealthCardDto getHealthCardByPetId(Integer petId);

    /**
     * Atualiza as informações de um cartão de saúde existente.
     * @param id O ID do HealthCard a ser atualizado.
     * @param healthCardDto O DTO com as novas informações.
     * @return O DTO do HealthCard atualizado.
     */
    HealthCardDto updateHealthCard(Integer id, HealthCardDto healthCardDto);

    /**
     * Deleta um cartão de saúde pelo seu ID.
     * @param id O ID do HealthCard a ser deletado.
     */
    void deleteHealthCard(Integer id);
}
