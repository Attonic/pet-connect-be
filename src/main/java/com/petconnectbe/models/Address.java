
package com.petconnectbe.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "address_tb")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Address implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "cep", nullable = false, length = 9)
    private String cep;

    @Column(name = "street", nullable = false, length = 100)
    private String street;

    @Column(name = "neighborhood", nullable = false, length = 100)
    private String neighborhood;

    @Column(name = "city", nullable = false, length = 100)
    private String city;

    @Column(name = "uf", nullable = false, length = 2)
    private String uf;

    /**
     * Relacionamento um-para-um com a entidade User (tutor).
     * Um endereço pertence a um único usuário.
     * A anotação @JoinColumn especifica a chave estrangeira na tabela de endereços.
     * @JsonIgnore é crucial para evitar recursão infinita durante a serialização JSON.
     */
    @OneToOne
    @JoinColumn(name = "tutor_id", referencedColumnName = "id")
    @JsonIgnore
    private User tutor;
}
