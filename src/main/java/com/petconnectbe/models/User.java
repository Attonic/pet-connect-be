package com.petconnectbe.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@Table(name = "user_tb")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "type", nullable = false, length = 30)
    private String type;

    @Column(name = "name",nullable = false, length = 150)
    private String name;

    @Column(name = "email", nullable = false, length = 254)
    private String email;

    @Column(name = "phone", nullable = false, length = 20)
    private String phone;

    @Column(name = "birth_fundation_date", nullable = false)
    private LocalDate birthOrFoundationDate;

    @Column(name = "cpf_cnpj", nullable = false, length = 18)
    private String cpfOrCnpj;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "adress_id")
    private Address address;

    @Column(name = "passeord", length = 100)
    private String password;


}
