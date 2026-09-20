package com.colegio.repository;

import com.colegio.model.Profesor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repositorio Spring Data JPA para la entidad Profesor.
 * Gestiona operaciones CRUD y consultas derivadas automáticas.
 */
@Repository
public interface ProfesorRepository extends JpaRepository<Profesor, UUID> {

    Optional<Profesor> findByEmail(String email);

    Optional<Profesor> findByLegajo(Integer legajo);

    Optional<Profesor> findByDni(String dni);

    boolean existsByEmail(String email);

    boolean existsByLegajo(Integer legajo);
}

