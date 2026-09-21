package com.clubdeportivo.repository;

import com.clubdeportivo.entity.Actividad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * =========================================================================================
 * REPOSITORIO: ActividadRepository
 * =========================================================================================
 * Persistencia y consultas para catálogo de disciplinas deportivas.
 */
@Repository
public interface ActividadRepository extends JpaRepository<Actividad, Long> {

    Optional<Actividad> findByCodigo(String codigo);

    boolean existsByCodigo(String codigo);
}

