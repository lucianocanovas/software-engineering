package com.clubdeportivo.repository;

import com.clubdeportivo.entity.Cuota;
import com.clubdeportivo.entity.enums.EstadoCuota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * =========================================================================================
 * REPOSITORIO: CuotaRepository
 * =========================================================================================
 * Consultas para control financiero de cuotas familiares y de socios titulares.
 */
@Repository
public interface CuotaRepository extends JpaRepository<Cuota, Long> {

    List<Cuota> findBySocioTitularIdOrderByFechaVencimientoDesc(Long socioId);

    List<Cuota> findByGrupoFamiliarIdOrderByFechaVencimientoDesc(Long grupoFamiliarId);

    List<Cuota> findByEstado(EstadoCuota estado);

    @Query("SELECT c FROM Cuota c WHERE c.socioTitular.id = :socioId AND c.estado IN (:estados) ORDER BY c.fechaVencimiento ASC")
    List<Cuota> findCuotasImpagas(@Param("socioId") Long socioId, @Param("estados") List<EstadoCuota> estados);

    Optional<Cuota> findBySocioTitularIdAndPeriodo(Long socioId, String periodo);

    long countByEstado(EstadoCuota estado);
}

