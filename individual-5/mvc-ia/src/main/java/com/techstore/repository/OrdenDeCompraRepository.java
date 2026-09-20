package com.techstore.repository;

import com.techstore.model.EstadoOrden;
import com.techstore.model.OrdenDeCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * =============================================================================
 * CAPA REPOSITORIO: OrdenDeCompraRepository
 * =============================================================================
 * Maneja las transacciones de compras mayoristas que actualizan el inventario.
 */
@Repository
public interface OrdenDeCompraRepository extends JpaRepository<OrdenDeCompra, Long> {

    Optional<OrdenDeCompra> findByNumero(String numero);

    List<OrdenDeCompra> findByEstado(EstadoOrden estado);

    List<OrdenDeCompra> findAllByOrderByFechaEmisionDesc();

    @Query("SELECT COUNT(o) FROM OrdenDeCompra o WHERE o.estado = 'PENDIENTE'")
    long countOrdenesPendientes();
}

