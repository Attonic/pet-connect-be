package com.petconnectbe.models;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Entidade que representa um usuário do tipo Clínica Veterinária.
 * Herda os campos comuns da classe User e adiciona campos específicos como CNPJ e data de fundação.
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("CLINICA")
public class Clinica extends User {

    /**
     * Cadastro Nacional da Pessoa Jurídica (CNPJ).
     * O formato esperado é "xx.xxx.xxx/xxxx-xx".
     * Deve ser único. Campo obrigatório.
     */
    @Column(name = "cnpj", unique = true, length = 18)
    private String cnpj;

    /**
     * Data de fundação da clínica.
     */
    @Column(name = "foundation_date")
    private LocalDate foundationDate;
}
