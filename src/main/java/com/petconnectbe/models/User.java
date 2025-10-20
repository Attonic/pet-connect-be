package com.petconnectbe.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Entidade JPA que representa um Usuário no banco de dados.
 * Pode ser tanto uma pessoa física (tutor) quanto uma pessoa jurídica (abrigo).
 * Mapeada para a tabela "user_tb".
 */
@Entity
@Data
@Table(name = "user_tb")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Serializable {

    /**
     * Chave primária da entidade Usuário.
     * É um UUID gerado automaticamente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private UUID id;

    /**
     * Tipo de usuário (ex: "TUTOR", "ABRIGO").
     * Campo obrigatório para diferenciar os tipos de conta.
     */
    @Column(name = "type", nullable = false, length = 30)
    private String type;

    /**
     * Nome completo do usuário ou razão social do abrigo.
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
     * Data de nascimento (para pessoa física) ou data de fundação (para pessoa jurídica).
     * Campo obrigatório.
     */
    @Column(name = "birth_foundation_date", nullable = false)
    private LocalDate birthOrFoundationDate;

    /**
     * Cadastro de Pessoa Física (CPF) ou Cadastro Nacional da Pessoa Jurídica (CNPJ).
     * O formato esperado é "xxx.xxx.xxx-xx" para CPF e "xx.xxx.xxx/xxxx-xx" para CNPJ.
     * Deve ser único. Campo obrigatório.
     */
    @Column(name = "cpf_cnpj", nullable = false, unique = true, length = 18)
    private String cpfOrCnpj;

    /**
     * Relacionamento Um-para-Um com a entidade Address.
     * - `cascade = CascadeType.ALL`: Operações de persistência, atualização e remoção
     *   no usuário serão cascateadas para o endereço associado.
     * - `@JoinColumn`: Especifica a chave estrangeira na tabela de usuários.
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
