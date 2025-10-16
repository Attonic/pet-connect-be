package com.petconnectbe.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa a carteirinha de saúde de um Pet.
 * Cada Pet tem um único PetCard associado.
 */
@Entity
@Table(name = "pet_cards")
@Data
@NoArgsConstructor
public class PetCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * O Pet ao qual esta carteirinha pertence.
     * A anotação @OneToOne estabelece uma relação de um-para-um com a entidade Pet.
     * `fetch = FetchType.LAZY`: O Pet associado só será carregado do banco de dados quando for acessado pela primeira vez.
     * `optional = false`: Garante que um PetCard não pode existir sem um Pet associado.
     * `@JoinColumn`: Especifica a coluna de chave estrangeira (pet_id) na tabela `pet_cards`.
     */
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pet_id", referencedColumnName = "id", unique = true)
    private Pet pet;

    /**
     * Alergias conhecidas do Pet (texto livre).
     * Ex: "Pólen, Pulgas, alguns tipos de grãos".
     */
    private String allergies;

    /**
     * O tipo sanguíneo do Pet.
     * Ex: "DEA 1.1 Positivo".
     */
    private String bloodType;
}
