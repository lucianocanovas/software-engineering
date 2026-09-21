package com.clubdeportivo.repository;

import com.clubdeportivo.entity.RegistroAcceso;
import com.clubdeportivo.entity.enums.TipoMovimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * =========================================================================================
 * REPOSITORIO: RegistroAccesoRepository
 * =========================================================================================
 * Registro de movimientos de entrada y salida con consultas analíticas para aforo y permanencia.
 */
@Repository
public interface RegistroAccesoRepository extends JpaRepository<RegistroAcceso, Long> {

    List<RegistroAcceso> findByPersonaIdOrderByFechaHoraEntradaDesc(Long personaId);

    List<RegistroAcceso> findTop20ByOrderByFechaHoraEntradaDesc();

    @Query("SELECT r FROM RegistroAcceso r WHERE r.persona.id = :personaId AND r.tipoMovimiento = :tipo AND r.fechaHoraSalida IS NULL ORDER BY r.fechaHoraEntrada DESC")
    List<RegistroAcceso> findUltimaEntradaSinSalida(Long personaId, TipoMovimiento tipo);

    long countByTipoMovimientoAndFechaHoraEntradaBetween(TipoMovimiento tipo, LocalDateTime inicio, LocalDateTime fin);
}

