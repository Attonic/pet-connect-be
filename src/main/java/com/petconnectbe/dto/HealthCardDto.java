package com.petconnectbe.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * DTO para a entidade HealthCard. Usado para transferir dados do HealthCard entre o frontend e o backend.
 */
@Data
public class HealthCardDto {

    private Integer id;

    /**
     * O ID do Pet ao qual este cartão de saúde está associado.
     * Este campo é essencial para vincular o HealthCard ao Pet correto ao criar ou atualizar.
     */
    @NotNull(message = "O ID do pet é obrigatório.")
    private Integer petId;

    /**
     * Alergias conhecidas do Pet.
     */
    private String allergies;

    /**
     * O tipo sanguíneo do Pet.
     */
    private String bloodType;
}
