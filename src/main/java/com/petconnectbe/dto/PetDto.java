package com.petconnectbe.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PetDto {

    private UUID id;

    private UUID tutorId;

    private String name;

    private String petType;

    private Double weight;

    private LocalDate birthDate;

    private String sex;

    private String microchipNumber;

    private String about;

    private String imageUrl;

    private String breed;

    private String size;

    private String coatType;
}
