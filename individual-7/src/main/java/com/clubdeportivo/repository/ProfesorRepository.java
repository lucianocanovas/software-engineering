package com.clubdeportivo.repository;

import com.clubdeportivo.entity.Profesor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * =========================================================================================
 * REPOSITORIO: ProfesorRepository
 * =========================================================================================
 * Persistencia y consultas para profesores e instructores de actividades.
 */
@Repository
public interface ProfesorRepository extends JpaRepository<Profesor, Long> {

    Optional<Profesor> findByMatricula(String matricula);

    boolean existsByMatricula(String matricula);
}

