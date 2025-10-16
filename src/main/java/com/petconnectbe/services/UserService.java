package com.petconnectbe.services;

import com.petconnectbe.dto.UserDto;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Define o contrato para o serviço de gerenciamento de Usuários.
 * <p>
 * Esta interface abstrai a lógica de negócio para todas as operações relacionadas a usuários,
 * como criação, busca, atualização e exclusão.
 * A implementação deste serviço será responsável por coordenar as interações com a camada de
 * persistência e garantir a integridade dos dados do usuário.
 */
public interface UserService {

    /**
     * Cria um novo usuário no sistema.
     *
     * @param userDto O DTO contendo as informações do novo usuário.
     * @return O {@link UserDto} representando o usuário que foi salvo.
     */
    UserDto save(UserDto userDto);

    /**
     * Busca um usuário pelo seu identificador único (ID).
     *
     * @param id O UUID do usuário a ser buscado.
     * @return Um {@link Optional} contendo o {@link UserDto} se o usuário for encontrado,
     *         ou {@link Optional#empty()} caso contrário.
     */
    Optional<UserDto> findById(UUID id);

    /**
     * Retorna uma lista com todos os usuários cadastrados no sistema.
     *
     * @return Uma lista de {@link UserDto}.
     */
    List<UserDto> findAll();

    /**
     * Atualiza as informações de um usuário existente.
     *
     * @param id O UUID do usuário a ser atualizado.
     * @param userDto O DTO contendo as novas informações do usuário.
     * @return O {@link UserDto} representando o usuário com as informações atualizadas.
     */
    UserDto update(UUID id, UserDto userDto);

    /**
     * Exclui um usuário do sistema com base no seu ID.
     *
     * @param id O UUID do usuário a ser excluído.
     */
    void deleteById(UUID id);

}
