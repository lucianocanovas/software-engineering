package com.clubdeportivo.repository;

import com.clubdeportivo.entity.PuntoDeAcceso;
import com.clubdeportivo.entity.enums.EstadoPuntoAcceso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * =========================================================================================
 * REPOSITORIO: PuntoDeAccesoRepository
 * =========================================================================================
 * Gestión física de terminales biométricas, molinetes y puertas de control de paso.
 */
@Repository
public interface PuntoDeAccesoRepository extends JpaRepository<PuntoDeAcceso, Long> {

    List<PuntoDeAcceso> findByEstado(EstadoPuntoAcceso estado);
}

