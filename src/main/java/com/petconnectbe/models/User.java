package com.petconnectbe.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

/**
 * Entidade JPA que representa a classe base para um Usuário no banco de dados.
 * Esta é uma classe abstrata que utiliza a estratégia de herança SINGLE_TABLE.
 * Todos os tipos de usuários (Tutor, Ong, Clinica) serão armazenados na tabela "user_tb",
 * e uma coluna "user_type" irá diferenciar o tipo de cada registro.
 */
@Entity
@Data
@Table(name = "user_tb")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
public abstract class User implements Serializable {

    /**
     * Chave primária da entidade Usuário.
     * É um UUID gerado automaticamente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private UUID id;

    /**
     * Nome completo do usuário ou razão social da pessoa jurídica.
     * Campo obrigatório.
     */
    @Column(name = "name", nullable = false, length = 150)
    private String name;

    /**
     * Endereço de e-mail do usuário.
     * Utilizado para login e comunicação. Deve ser único. Campo obrigatório.
     */
    @Column(name = "email", nullable = false, unique = true, length = 254)
    private String email;

    /**
     * Número de telefone para contato.
     * Campo obrigatório.
     */
    @Column(name = "phone", nullable = false, length = 20)
    private String phone;

    /**
     * Relacionamento Um-para-Um com a entidade Address.
     * - `cascade = CascadeType.ALL`: Operações no usuário serão cascateadas para o endereço.
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    /**
     * Senha do usuário, armazenada de forma criptografada (hash).
     */
    @Column(name = "password", length = 100)
    private String password;
}
