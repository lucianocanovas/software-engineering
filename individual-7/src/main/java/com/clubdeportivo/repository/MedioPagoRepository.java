package com.clubdeportivo.repository;

import com.clubdeportivo.entity.MedioPago;
import com.clubdeportivo.entity.enums.TipoMedioPago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * =========================================================================================
 * REPOSITORIO: MedioPagoRepository
 * =========================================================================================
 * Consultas polimórficas para medios de pago (Efectivo, Transferencia, Mercado Pago).
 */
@Repository
public interface MedioPagoRepository extends JpaRepository<MedioPago, Long> {

    List<MedioPago> findByTipo(TipoMedioPago tipo);
}

