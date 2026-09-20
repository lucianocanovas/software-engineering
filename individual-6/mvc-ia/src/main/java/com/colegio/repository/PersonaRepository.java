package com.colegio.repository;

import com.colegio.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repositorio Spring Data JPA para la entidad base Persona.
 * Empleado principalmente para operaciones de autenticación y verificación de emails/usuarios únicos.
 */
@Repository
public interface PersonaRepository extends JpaRepository<Persona, UUID> {

    Optional<Persona> findByEmail(String email);

    boolean existsByEmail(String email);
}

