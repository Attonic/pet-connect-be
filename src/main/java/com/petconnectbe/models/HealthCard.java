package com.petconnectbe.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the health card of a Pet.
 * Each Pet has a single HealthCard associated with it.
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
     * The Pet to which this health card belongs.
     * The @OneToOne annotation establishes a one-to-one relationship with the Pet entity.
     * `fetch = FetchType.LAZY`: The associated Pet will only be loaded from the database when accessed for the first time.
     * `optional = false`: Ensures that a HealthCard cannot exist without an associated Pet.
     * `@JoinColumn`: Specifies the foreign key column (pet_id) in the `health_cards` table.
     */
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pet_id", referencedColumnName = "id", unique = true)
    private Pet pet;

    /**
     * Known allergies of the Pet (free text).
     * Ex: "Pollen, Fleas, some types of grains".
     */
    private String allergies;

    /**
     * The blood type of the Pet.
     * Ex: "DEA 1.1 Positive".
     */
    private String bloodType;
}
