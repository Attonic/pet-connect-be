package com.petconnectbe.repositories;

import com.petconnectbe.models.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EnderecoRepository extends JpaRepository<Endereco, UUID> {
    Optional<Endereco> findEnderecoByEstadoUF(String estadoUF);
}
