package com.petconnectbe.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressDto {

    private UUID id;

    @NotBlank(message = "O CEP é obrigatório.")
    @Size(min = 8, max = 8, message = "O CEP deve conter 8 dígitos.")
    private String cep;

    @NotBlank(message = "A Rua é obrigatório.")
    private String street;

    @NotBlank(message = "O Bairro é obrigatório.")
    private String neighborhood;

    @NotBlank(message = "A Cidade é obrigatório.")
    private String city;

    @NotBlank(message = "O UF é obrigatório.")
    @Size(min = 2, max = 2, message = "A UF deve ter 2 Caracteres ex: MA, CE.")
    private String uf;


}
