package com.clubdeportivo.repository;

import com.clubdeportivo.entity.FamiliarAdherente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * =========================================================================================
 * REPOSITORIO: FamiliarAdherenteRepository
 * =========================================================================================
 * Operaciones de acceso a datos para Familiares Adherentes del club.
 */
@Repository
public interface FamiliarAdherenteRepository extends JpaRepository<FamiliarAdherente, Long> {

    Optional<FamiliarAdherente> findByDni(String dni);

    boolean existsByDni(String dni);

    List<FamiliarAdherente> findByGrupoFamiliarId(Long grupoFamiliarId);
}

