package com.petconnectbe.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * DTO (Data Transfer Object) para dados de Pet.
 * Usado para transferir informações de pets entre a camada de controle e os clientes (ex: frontend).
 * O Lombok @Data gera automaticamente getters, setters, toString, equals e hashCode.
 * O Lombok @NoArgsConstructor gera um construtor sem argumentos.
 */
@Data
@NoArgsConstructor
public class PetDto {

    /**
     * O identificador único do pet. Gerado pelo banco de dados.
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
     * A espécie do pet (ex: Cachorro, Gato).
     */
    private String species;

    /**
     * A raça ou raças do pet.
     */
    private String races;

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
     * A URL da imagem do pet. Este campo é populado pelo backend após o upload.
     */
    private String imageUrl;
}
