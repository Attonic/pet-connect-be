package com.petconnectbe.models;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@DiscriminatorValue("TUTOR")
public class Tutor extends User {

    @Column(name = "cpf", unique = true)
    private String cpf;

    @Column(name = "birth_date")
    private LocalDate birthDate;
}
