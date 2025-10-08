package com.petconnectbe.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidade JPA que representa um Pet no banco de dados.
 * Mapeada para a tabela "pets". Contém todas as informações
 * relevantes sobre o animal de estimação.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "pets")
public class Pet {

    /**
     * Chave primária da entidade Pet.
     * O valor é gerado automaticamente pelo banco de dados (estratégia de identidade).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Nome do pet. Campo obrigatório.
     */
    @Column(nullable = false)
    private String name;

    /**
     * Espécie do pet (ex: "Cachorro", "Gato", "Coelho").
     */
    private String species;

    /**
     * Raça do pet.
     */
    private String races;

    /**
     * Peso do pet, geralmente em quilogramas.
     */
    private Double weight;

    /**
     * Idade do pet em anos.
     */
    private Integer age;

    /**
     * Sexo do pet (ex: "Macho", "Fêmea").
     */
    private String sex;

    /**
     * Número de identificação do microchip do pet, caso possua.
     */
    private String microchipNumber;

    /**
     * Campo de texto livre para informações adicionais sobre o pet,
     * como comportamento, histórico médico, etc.
     * Mapeado para um tipo de coluna que suporta textos longos.
     */
    @Column(columnDefinition = "TEXT")
    private String about;

    /**
     * URL da imagem de perfil do pet. Pode ser um link para um serviço de
     * armazenamento de arquivos ou um caminho local servido pela aplicação.
     */
    private String imageUrl;

    /**
     * Relacionamento Muitos-para-Um com a entidade User, representando o tutor do pet.
     * - `fetch = FetchType.LAZY`: O tutor não é carregado do banco de dados até que seja explicitamente acessado.
     * - `nullable = false`: Todo pet deve obrigatoriamente ter um tutor associado.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tutor_id", nullable = false)
    private User tutor;
}
