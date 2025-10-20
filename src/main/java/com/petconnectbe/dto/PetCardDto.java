package com.petconnectbe.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * DTO para a entidade PetCard. Usado para transferir dados do PetCard entre o frontend e o backend.
 */
@Data
public class PetCardDto {

    private Integer id;

    /**
     * O ID do Pet ao qual este cartão de saúde está associado.
     * Este campo é essencial para vincular o PetCard ao Pet correto ao criar ou atualizar.
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

    /**
     * Campo de texto para observações gerais sobre a saúde do pet,
     * como cirurgias prévias, condições crônicas, etc.
     */
    private String healthConditions;
}
