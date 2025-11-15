
package com.petconnectbe.services;

import com.petconnectbe.dto.VaccineDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Interface para o serviço de Vacinas.
 * Define as operações de negócio para gerenciar as Vacinas de um Pet,
 * garantindo que todas as operações sejam validadas contra o dono do pet.
 */
public interface VaccineService {

    /**
     * Cria um novo registro de vacina e o associa a um pet.
     * A implementação deve verificar se o pet pertence ao usuário antes de criar.
     * @param userId O ID do usuário (para validação de propriedade).
     * @param petId O ID do pet que receberá a vacina.
     * @param vaccineDto Os dados da vacina a ser criada.
     * @return O DTO da vacina criada.
     */
    VaccineDto createVaccine(UUID userId, UUID petId, VaccineDto vaccineDto);

    /**
     * Encontra todas as vacinas de um pet específico.
     * A implementação deve verificar a propriedade do pet.
     * @param userId O ID do usuário (para validação).
     * @param petId O ID do pet.
     * @return Uma lista de DTOs das vacinas encontradas.
     */
    List<VaccineDto> findAllByPetId(UUID userId, UUID petId);

    /**
     * Busca uma vacina específica pelo seu ID, validando a propriedade do pet.
     * @param userId O ID do usuário (para validação).
     * @param petId O ID do pet.
     * @param vaccineId O ID da vacina a ser buscada.
     * @return Um Optional contendo o DTO da vacina se encontrada.
     */
    Optional<VaccineDto> findVaccineById(UUID userId, UUID petId, UUID vaccineId);

    /**
     * Atualiza uma vacina existente.
     * @param userId O ID do usuário (para validação).
     * @param petId O ID do pet.
     * @param vaccineId O ID da vacina a ser atualizada.
     * @param vaccineDto Os novos dados da vacina.
     * @return O DTO da vacina atualizada.
     */
    VaccineDto updateVaccine(UUID userId, UUID petId, UUID vaccineId, VaccineDto vaccineDto);

    /**
     * Deleta uma vacina, verificando a propriedade antes da deleção.
     * @param userId O ID do usuário (para validação).
     * @param petId O ID do pet.
     * @param vaccineId O ID da vacina a ser deletada.
     */
    void deleteVaccine(UUID userId, UUID petId, UUID vaccineId);
}
