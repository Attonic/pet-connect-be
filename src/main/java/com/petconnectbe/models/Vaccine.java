package com.petconnectbe.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Entidade que representa um registro de vacina para um pet.
 * Cada registro está associado a um PetCard.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "vaccines")
public class Vaccine {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate applicationDate;

    private LocalDate nextDoseDate;

    @Column(nullable = false)
    private String lot;

    /**
     * Relacionamento Muitos-para-Um com a entidade PetCard.
     * Muitas vacinas podem pertencer a um único PetCard.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_card_id", nullable = false)
    private PetCard petCard;
}
