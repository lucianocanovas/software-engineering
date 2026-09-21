package com.clubdeportivo.repository;

import com.clubdeportivo.entity.GrupoFamiliar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * =========================================================================================
 * REPOSITORIO: GrupoFamiliarRepository
 * =========================================================================================
 * Operaciones CRUD y consultas especializadas para suscripciones de Grupos Familiares.
 */
@Repository
public interface GrupoFamiliarRepository extends JpaRepository<GrupoFamiliar, Long> {

    Optional<GrupoFamiliar> findByCodigo(String codigo);

    Optional<GrupoFamiliar> findByTitularId(Long socioId);

    boolean existsByCodigo(String codigo);

    @Query("SELECT g FROM GrupoFamiliar g LEFT JOIN FETCH g.integrantes WHERE g.id = :id")
    Optional<GrupoFamiliar> findByIdConIntegrantes(Long id);
}

