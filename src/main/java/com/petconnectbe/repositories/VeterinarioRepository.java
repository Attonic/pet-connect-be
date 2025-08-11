package com.petconnectbe.repositories;

import com.petconnectbe.models.Veterinario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface VeterinarioRepository extends JpaRepository<Veterinario, UUID> {

    Optional<Veterinario> findAllByCrmv(String crmv);
    Optional<Veterinario> findByEmail(String email);

    Optional<Object> findByCrmv(String crmv);
}
