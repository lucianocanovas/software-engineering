package com.clubdeportivo.repository;

import com.clubdeportivo.entity.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * =========================================================================================
 * REPOSITORIO: PersonaRepository
 * =========================================================================================
 * Capa de persistencia JPA para la entidad abstracta Persona. Permite búsquedas polimórficas
 * en toda la jerarquía de personas (socios, adherentes y empleados).
 */
@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {

    Optional<Persona> findByDni(String dni);

    boolean existsByDni(String dni);
}

