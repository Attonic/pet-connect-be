package com.petconnectbe.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "clinical_histories")
@Data
@NoArgsConstructor
public class ClinicalHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private LocalDate dataDaConsulta;

    @Column(nullable = false)
    private String local;

    @Column(nullable = false)
    private String tipo;

    @Column(nullable = false)
    private String retorno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;
}
