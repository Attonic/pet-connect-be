
package com.petconnectbe.services;

import com.petconnectbe.dto.AddressDto;
import com.petconnectbe.models.Address;

import java.util.Optional;
import java.util.UUID;

/**
 * Interface que define o contrato para as operações de negócio relacionadas ao Endereço de um Usuário.
 * A lógica garante que um endereço está sempre associado a um usuário específico.
 */
public interface AddressService {

    /**
     * Busca o endereço associado a um ID de usuário específico.
     *
     * @param userId O UUID do usuário.
     * @return Um Optional contendo o AddressDto se um endereço for encontrado para o usuário.
     */
    Optional<AddressDto> findAddressByUserId(UUID userId);

    /**
     * Cria ou atualiza o endereço para um usuário específico.
     * Se o usuário já possui um endereço, ele é atualizado. Caso contrário, um novo é criado.
     * Este método encapsula a lógica de "upsert".
     *
     * @param userId O UUID do usuário ao qual o endereço pertence.
     * @param addressDto O DTO com os dados do endereço.
     * @return O AddressDto do endereço salvo (criado ou atualizado).
     */
    AddressDto createOrUpdateAddress(UUID userId, AddressDto addressDto);

    /**
     * Deleta o endereço associado a um ID de usuário específico.
     * A implementação deve garantir que apenas o endereço do usuário correspondente seja removido.
     *
     * @param userId O UUID do usuário cujo endereço será deletado.
     */
    void deleteAddressByUserId(UUID userId);

    /**
     * Converte uma entidade {@link Address} para seu respectivo {@link AddressDto}.
     *
     * @param address A entidade a ser convertida.
     * @return O DTO correspondente.
     */
    AddressDto toDto(Address address);

    /**
     * Converte um {@link AddressDto} para sua respectiva entidade {@link Address}.
     *
     * @param addressDto O DTO a ser convertido.
     * @return A entidade correspondente.
     */
    Address toEntity(AddressDto addressDto);
}
