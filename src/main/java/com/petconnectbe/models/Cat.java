package com.petconnectbe.models;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Entidade que representa um Pet do tipo Gato.
 * Herda os campos comuns da classe Pet e adiciona campos específicos como raça.
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("CAT")
public class Cat extends Pet {

    /**
     * Raça do gato.
     */
    @Column(name = "breed")
    private String breed;
}
