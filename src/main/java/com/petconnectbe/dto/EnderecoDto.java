package com.petconnectbe.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EnderecoDto {

    private UUID id;

    @NotBlank(message = "O CEP é obrigatório.")
    private String cep;

    @NotBlank(message = "A Rua é obrigatório.")
    private String rua;

    @NotBlank(message = "O Bairro é obrigatório.")
    private String bairro;

    @NotBlank(message = "O Complemento é obrigatório.")
    private String complemento;

    @NotBlank(message = "A Cidade é obrigatório.")
    private String cidade;

    @NotBlank(message = "O UF é obrigatório.")
    @Size(min = 2, max = 2, message = "A UF deve ter 2 Caracteres ex: MA, CE.")
    private String estadoUF;


}
