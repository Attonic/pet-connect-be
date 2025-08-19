package com.petconnectbe.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    @Column(name = "address_id")
    @GeneratedValue(generator = "UUID")
    private UUID addressId;

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


}
