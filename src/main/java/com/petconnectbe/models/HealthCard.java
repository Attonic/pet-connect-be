package com.petconnectbe.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa o cartão de saúde de um Pet.
 * Cada Pet possui um único HealthCard associado a ele.
 */
@Entity
@Table(name = "health_cards")
@Data
@NoArgsConstructor
public class HealthCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * O Pet ao qual este cartão de saúde pertence.
     * A anotação @OneToOne estabelece uma relação um-para-um com a entidade Pet.
     * `fetch = FetchType.LAZY`: O Pet associado só será carregado do banco de dados quando for acessado pela primeira vez.
     * `optional = false`: Garante que um HealthCard não pode existir sem um Pet associado.
     * `@JoinColumn`: Especifica a coluna de chave estrangeira (pet_id) na tabela `health_cards`.
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
