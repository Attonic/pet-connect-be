package com.petconnectbe.dto;

import com.petconnectbe.models.Endereco;
import com.petconnectbe.models.Veterinario;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
@JsonIgnoreProperties(ignoreUnknown = true)
public class VeterinarioDto implements Serializable {

    @NotBlank(message = "O Nome é Obrigatório.")
    private String nome;

    @NotBlank(message = "O E-Mail é Obrigatório.")
    @Email(message = "Formato de e-mail inválido.")
    private String email;

    @NotNull(message = "A Data de Nascimento é Obrigatório.")
    private LocalDate dataNascimento;

    @NotBlank(message = "O CRMV é Obrigatório.")
    private String crmv;

    @NotBlank(message = "A Senha é Obrigatório.")
    private String senha;

    @NotBlank(message = "O Endereço é Obrigatório.")
    @Valid
    private EnderecoDto endereco;

    @AssertTrue(message = "Você deve aceitar os Termos de Responsabilidade")
    private boolean aceitaTermos;


}


