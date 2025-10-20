package com.petconnectbe.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa o cartão de um Pet, chamado de PetCard.
 * Cada Pet possui um único PetCard associado a ele.
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
     * O Pet ao qual este cartão pertence.
     * A anotação @OneToOne estabelece uma relação um-para-um com a entidade Pet.
     */
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pet_id", referencedColumnName = "id", unique = true)
    private Pet pet;

    /**
     * Alergias conhecidas do Pet (texto livre).
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
    @Column(columnDefinition = "TEXT")
    private String healthConditions;
}
