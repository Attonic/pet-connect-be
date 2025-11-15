
package com.petconnectbe.repositories;

import com.petconnectbe.models.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repositório Spring Data JPA para a entidade {@link Pet}.
 * <p>
 * Fornece métodos CRUD (Create, Read, Update, Delete) para a entidade Pet
 * e métodos de consulta personalizados que o Spring Data JPA implementa automaticamente.
 */
public interface PetRepository extends JpaRepository<Pet, UUID> {

    /**
     * Encontra todos os Pets associados a um ID de tutor específico.
     * O Spring Data JPA cria a consulta com base no nome do método.
     * Ele buscará por um campo 'tutor' na entidade Pet e filtrará pelo 'id' desse tutor.
     * @param tutorId O UUID do tutor.
     * @return Uma lista de Pets pertencentes ao tutor.
     */
    List<Pet> findByTutorId(UUID tutorId);

    /**
     * Encontra um Pet específico pelo seu ID e pelo ID de seu tutor.
     * Isso é crucial para garantir que um usuário só possa acessar/modificar seus próprios pets.
     * @param id O UUID do Pet.
     * @param tutorId O UUID do tutor.
     * @return Um Optional contendo o Pet se a combinação for encontrada.
     */
    Optional<Pet> findByIdAndTutorId(UUID id, UUID tutorId);

    /**
     * Verifica se um Pet existe com um determinado ID e se pertence a um tutor específico.
     * É uma maneira mais eficiente do que buscar a entidade inteira quando você só precisa saber se ela existe.
     * @param id O UUID do Pet.
     * @param tutorId O UUID do tutor.
     * @return true se o Pet existe e pertence ao tutor, false caso contrário.
     */
    boolean existsByIdAndTutorId(UUID id, UUID tutorId);
}
