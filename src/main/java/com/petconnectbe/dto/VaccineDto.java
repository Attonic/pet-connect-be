package com.petconnectbe.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class VaccineDto {
    private Integer id;
    private String name;
    private LocalDate applicationDate;
    private LocalDate nextDoseDate;
    private String lot;
    private Integer petCardId;
}
