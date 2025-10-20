package com.petconnectbe.services;

import com.petconnectbe.dto.AddressDto;
import com.petconnectbe.models.Address;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Interface que define o contrato para as operações de negócio relacionadas a Endereços.
 * <p>
 * Esta camada de serviço abstrai a lógica de negócio e as interações com o repositório
 * de dados para a entidade {@link Address}.
 */
public interface AddressService {

    /**
     * Salva ou atualiza um endereço no banco de dados.
     *
     * @param dto O DTO contendo as informações do endereço a ser salvo.
     * @return O {@link AddressDto} representando o endereço salvo.
     */
    AddressDto save(AddressDto dto);

    /**
     * Retorna uma lista com todos os endereços cadastrados.
     *
     * @return Uma lista de {@link AddressDto}.
     */
    List<AddressDto> findAll();

    /**
     * Busca um endereço pelo seu identificador único (ID).
     *
     * @param id O UUID do endereço a ser buscado.
     * @return Um {@link Optional} contendo o {@link AddressDto} se encontrado, ou {@link Optional#empty()} caso contrário.
     */
    Optional<AddressDto> findById(UUID id);

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
