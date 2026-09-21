package com.clubdeportivo.repository;

import com.clubdeportivo.entity.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * =========================================================================================
 * REPOSITORIO: EmpleadoRepository
 * =========================================================================================
 * Acceso a datos para el personal de planta del club deportivo.
 */
@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

    Optional<Empleado> findByLegajo(String legajo);

    boolean existsByLegajo(String legajo);
}

