package com.clubdeportivo.repository;

import com.clubdeportivo.entity.Pago;
import com.clubdeportivo.entity.enums.EstadoPago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * =========================================================================================
 * REPOSITORIO: PagoRepository
 * =========================================================================================
 * Persistencia y reportes de recaudación de pagos del club.
 */
@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {

    Optional<Pago> findByNumeroRecibo(String numeroRecibo);

    Optional<Pago> findByCuotaId(Long cuotaId);

    List<Pago> findBySocioIdOrderByFechaPagoDesc(Long socioId);

    List<Pago> findTop20ByOrderByFechaPagoDesc();

    @Query("SELECT COALESCE(SUM(p.montoAbonado), 0) FROM Pago p WHERE p.estado = :estado")
    BigDecimal sumTotalRecaudadoPorEstado(EstadoPago estado);
}

