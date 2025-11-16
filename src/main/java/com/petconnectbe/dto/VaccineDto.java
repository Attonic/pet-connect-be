package com.petconnectbe.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class VaccineDto {
    private UUID id;
    private String name;
    private LocalDate applicationDate;
    private LocalDate nextDoseDate;
    private String lot;
    private UUID petCardId;
}
