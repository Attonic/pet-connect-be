package com.petconnectbe.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonFormat;             //incluida para adaptar formato de data
import com.fasterxml.jackson.annotation.JsonProperty;           //incluida para corrigir endereco
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto implements Serializable {

    @NotBlank(message = "O tipo do Usuário deve ser informado.")
    private String type;

    @NotBlank(message = "O Nome é Obrigatório.")
    private String name;

    @NotBlank(message = "O E-Mail é Obrigatório.")
    @Email(message = "Formato de e-mail inválido.")
    private String email;

    @NotBlank(message = "O Telefone deve ser informado")
    private String phone;

    @NotNull(message = "A Data de Nascimento é Obrigatório.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") //Permite ajustar formato da data
    private LocalDate birthOrFoundationDate;

    @NotBlank(message = "A Senha é Obrigatório.")
    private String cpfOrCnpj;


    @NotBlank(message = "O Endereço é Obrigatório.")
    @Valid
    @JsonProperty("address")  //incluido
    private AddressDto endereco;

    @NotBlank(message = "A senha deve ser informada.")
    private String password;

}


