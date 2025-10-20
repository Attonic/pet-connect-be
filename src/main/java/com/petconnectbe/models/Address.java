package com.petconnectbe.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

/**
 * Entidade JPA que representa um Endereço no banco de dados.
 * Mapeada para a tabela "address_tb".
 * Implementa Serializable para suportar mecanismos de serialização do Java.
 */
@Entity
@Table(name = "address_tb")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Address implements Serializable {

    /**
     * Chave primária da entidade Endereço.
     * É um UUID gerado automaticamente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "address_id")
    private UUID id;

    /**
     * Código de Endereçamento Postal (CEP).
     * Armazenado como uma string de até 9 caracteres. Não pode ser nulo.
     */
    @Column(name = "cep", nullable = false, length = 9)
    private String cep;

    /**
     * Nome da rua, avenida ou logradouro.
     * Não pode ser nulo.
     */
    @Column(name = "street", nullable = false, length = 100)
    private String street;

    /**
     * Nome do bairro.
     * Não pode ser nulo.
     */
    @Column(name = "neighborhood", nullable = false, length = 100)
    private String neighborhood;

    /**
     * Nome da cidade.
     * Não pode ser nulo.
     */
    @Column(name = "city", nullable = false, length = 100)
    private String city;

    /**
     * Sigla da Unidade Federativa (Estado), com 2 caracteres.
     * Ex: "SP", "RJ". Não pode ser nulo.
     */
    @Column(name = "uf", nullable = false, length = 2)
    private String uf;
}
