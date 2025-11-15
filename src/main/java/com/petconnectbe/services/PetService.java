
package com.petconnectbe.services;

import com.petconnectbe.dto.PetDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Interface para o serviço de Pets.
 * Define as operações de negócio para gerenciar Pets, agora vinculadas a um Usuário.
 */
public interface PetService {

    /**
     * Cria um novo pet e o associa a um usuário.
     * @param userId O ID do usuário dono do pet.
     * @param petDto Os dados do pet a ser criado.
     * @param image A imagem (opcional) do pet.
     * @return O DTO do pet criado.
     */
    PetDto createPet(UUID userId, PetDto petDto, MultipartFile image);

    /**
     * Encontra todos os pets pertencentes a um usuário específico.
     * @param userId O ID do usuário.
     * @return Uma lista de DTOs dos pets encontrados.
     */
    List<PetDto> findAllByUserId(UUID userId);

    /**
     * Busca um pet específico pelo seu ID e pelo ID do seu dono.
     * Isso garante que um usuário só possa acessar seus próprios pets.
     * @param petId O ID do pet a ser buscado.
     * @param userId O ID do usuário dono.
     * @return Um Optional contendo o DTO do pet se encontrado.
     */
    Optional<PetDto> findByIdAndUserId(UUID petId, UUID userId);

    /**
     * Atualiza os dados de um pet existente.
     * @param petId O ID do pet a ser atualizado.
     * @param userId O ID do dono, para verificação de permissão.
     * @param petDto Os novos dados do pet.
     * @param image A nova imagem (opcional) do pet.
     * @return O DTO do pet atualizado.
     */
    PetDto updatePet(UUID petId, UUID userId, PetDto petDto, MultipartFile image);

    /**
     * Deleta um pet, verificando se a requisição vem do seu dono.
     * @param petId O ID do pet a ser deletado.
     * @param userId O ID do usuário dono.
     */
    void deleteByIdAndUserId(UUID petId, UUID userId);
}
