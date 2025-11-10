package com.petconnectbe.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

/**
 * DTO para a entidade PetCard. Usado para transferir dados do PetCard entre o frontend e o backend.
 */
@Data
public class PetCardDto {

    private UUID id;

    /**
     * O ID do Pet ao qual este cartão de saúde está associado.
     * Este campo é essencial para vincular o PetCard ao Pet correto ao criar ou atualizar.
     */
    @NotNull(message = "O ID do pet é obrigatório.")
    private UUID petId;
}
