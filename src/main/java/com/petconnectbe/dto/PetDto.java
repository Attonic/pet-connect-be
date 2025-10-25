package com.petconnectbe.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * DTO (Data Transfer Object) para dados de Pet.
 * Usado para transferir informações de pets entre a camada de controle e os clientes.
 * Esta versão é projetada para suportar uma estrutura polimórfica (Dog, Cat, etc.).
 * A anotação @JsonInclude(JsonInclude.Include.NON_NULL) garante que campos nulos
 * (como 'size' para um gato) não sejam incluídos na resposta JSON.
 */
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PetDto {

    /**
     * O identificador único do pet.
     */
    private Integer id;

    /**
     * O ID do tutor (usuário) associado a este pet.
     */
    private UUID tutorId;

    /**
     * O nome do pet.
     */
    private String name;

    /**
     * O tipo de pet (ex: "DOG", "CAT"). Este campo é usado como discriminador
     * para determinar a classe de entidade concreta a ser usada.
     */
    private String petType;

    /**
     * O peso do pet em quilogramas.
     */
    private Double weight;

    /**
     * A idade do pet em anos.
     */
    private Integer age;

    /**
     * O sexo do pet (ex: Macho, Fêmea).
     */
    private String sex;

    /**
     * O número do microchip de identificação, se houver.
     */
    private String microchipNumber;

    /**
     * Um campo de texto livre para informações adicionais sobre o pet.
     */
    private String about;

    /**
     * A URL da imagem do pet.
     */
    private String imageUrl;

    // Campos específicos para Dog e Cat
    /**
     * A raça do pet. Comum a Dog e Cat.
     */
    private String breed;

    // Campos específicos para Dog
    /**
     * O porte do cachorro (ex: "Pequeno", "Médio", "Grande").
     * Específico para a classe Dog.
     */
    private String size;

    // Campos específicos para Cat
    /**
     * O tipo de pelagem do gato (ex: "Curto", "Longo").
     * Específico para a classe Cat.
     */
    private String coatType;
}
