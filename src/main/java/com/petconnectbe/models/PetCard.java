package com.petconnectbe.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * Lista de vacinas associadas a este PetCard.
     * - @OneToMany: Define a relação um-para-muitos (um PetCard para muitas Vacinas).
     * - mappedBy = "petCard": Indica que o lado "muitos" (Vaccine) é o dono da relação.
     * - cascade = CascadeType.ALL: Operações de persistência no PetCard são aplicadas às vacinas.
     * - orphanRemoval = true: Se uma vacina é removida da lista, ela é excluída do banco.
     */
    @OneToMany(mappedBy = "petCard", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Vaccine> vaccines = new ArrayList<>();
}
